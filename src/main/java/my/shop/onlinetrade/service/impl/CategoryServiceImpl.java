package my.shop.onlinetrade.service.impl;

import my.shop.onlinetrade.entity.Category;
import my.shop.onlinetrade.repository.CategoryRepository;
import my.shop.onlinetrade.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves a map of category names and a list of their associated product names.
     *
     * @return a map containing category names and a list of associated product names
     */
    @Override
    public Map<String, Set<String>> getFilterableProductFeaturesMap(String category, String model) {
        List<Object[]> result = categoryRepository.findFilterableProductFeatures(category, model);

        Map<String, Set<String>> filterableProductFeatures = new HashMap<>();
        for (Object[] objects : result) {
            String categoryName = (String) objects[0];
            String productName = (String) objects[1];
            String productFeature = (String) objects[2];
            String featureValue = (String) objects[3];

            Set<String> featuresAndValues = filterableProductFeatures.getOrDefault(productFeature, new HashSet<>());
            featuresAndValues.add(featureValue);

            filterableProductFeatures.put(productFeature, featuresAndValues);
        }
        return filterableProductFeatures;
    }


}
