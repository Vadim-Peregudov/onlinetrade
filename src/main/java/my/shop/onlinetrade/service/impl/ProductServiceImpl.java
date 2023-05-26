package my.shop.onlinetrade.service.impl;

import jakarta.persistence.EntityNotFoundException;
import my.shop.onlinetrade.dto.FilterRequestDto;
import my.shop.onlinetrade.dto.FilterResponseDto;
import my.shop.onlinetrade.dto.ProductCharacteristicValuesDto;
import my.shop.onlinetrade.entity.Product;
import my.shop.onlinetrade.repository.ProductRepository;
import my.shop.onlinetrade.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Value("${page.size.product}")
    private int pageSize;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves a map of category names and a list of their associated product names.
     *
     * @return map of category names and a list of their associated product names
     */
    @Override
    public Map<String, List<String>> findCategoriesWithProductNames() {
        List<Object[]> result = productRepository.findCategoriesWithProductNames();

        Map<String, List<String>> categoriesWithProductNames = new HashMap<>();
        for (Object[] row : result) {
            String categoryName = (String) row[0];
            String productName = (String) row[1];

            List<String> productNames = categoriesWithProductNames.getOrDefault(categoryName, new ArrayList<>());
            productNames.add(productName);

            categoriesWithProductNames.put(categoryName, productNames);
        }

        return categoriesWithProductNames;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<String> findProductByCategory(String category) {
        return productRepository.findDistinctByNameByCategoryName(category);
    }

    @Override
    public FilterResponseDto searchProductsByCriteriaAndSorting(FilterRequestDto filterRequestDto) {
        var response = productRepository.searchProducts(filterRequestDto, pageSize);

        FilterResponseDto filterResponseDto = new FilterResponseDto();
        filterResponseDto.setProductList(response.toList());
        filterResponseDto.setTotalPage(response.getTotalPages());
        filterResponseDto.setPage(response.getPageable().getPageNumber() + 1);

        return filterResponseDto;
    }

    @Override
    public Product findProductById(long id) throws EntityNotFoundException {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            return optionalProduct.orElseThrow(() -> new EntityNotFoundException("Данный продукт не найден"));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Set<ProductCharacteristicValuesDto>> getCharacteristicsToProductsMapByCategory(Product product) {
        List<Object[]> result = productRepository.findNonMatchingProductFeatures(product.getCategory().getName(), product.getName(), product.getId());

        Map<String, Set<ProductCharacteristicValuesDto>> filterableProductFeatures = new HashMap<>();
        for (Object[] objects : result) {
            String features = (String) objects[0];
            Long productId = (Long) objects[1];
            String featureValue = (String) objects[2];

            Set<ProductCharacteristicValuesDto> featuresAndValues = filterableProductFeatures.getOrDefault(features, new HashSet<>());
            ProductCharacteristicValuesDto modelCharacteristicsMap = new ProductCharacteristicValuesDto(featureValue, productId);
            featuresAndValues.add(modelCharacteristicsMap);

            filterableProductFeatures.put(features, featuresAndValues);
        }
        return filterableProductFeatures;
    }

    @Override
    public Map<Product, Integer> getProductsByIds(Map<String, Integer> basket) {
        List<Long> ids = basket.keySet().stream().map(Long::valueOf).toList();
        List<Product> list = productRepository.findProductsByIds(ids);

        Map<Product, Integer> productIntegerMap = new HashMap<>();
        for (Product p : list) {
            productIntegerMap.put(p, basket.get(String.valueOf(p.getId())));
        }

        return productIntegerMap;
    }


}
