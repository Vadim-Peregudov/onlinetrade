package my.shop.onlinetrade.service;

import my.shop.onlinetrade.dto.FilterRequestDto;
import my.shop.onlinetrade.dto.FilterResponseDto;
import my.shop.onlinetrade.dto.ProductCharacteristicValuesDto;
import my.shop.onlinetrade.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {
    Map<String, List<String>> findCategoriesWithProductNames();

    List<Product> findAllProduct();

    List<String> findProductByCategory(String category);

    FilterResponseDto searchProductsByCriteriaAndSorting(FilterRequestDto filterParamDto);

    Product findProductById(long id);

    Map<String, Set<ProductCharacteristicValuesDto>> getCharacteristicsToProductsMapByCategory(Product product);

    Map<Product,Integer> getProductsByIds(Map<String, Integer> basket);

}
