package my.shop.onlinetrade.service;

import my.shop.onlinetrade.entity.Category;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CategoryService {
    List<Category> getAll();

    Map<String, Set<String>> getFilterableProductFeaturesMap(String category, String model);

}
