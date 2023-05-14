package my.shop.onlinetrade.controller;

import my.shop.onlinetrade.dto.FilterRequestDto;
import my.shop.onlinetrade.dto.FilterResponseDto;
import my.shop.onlinetrade.entity.Category;
import my.shop.onlinetrade.entity.Product;
import my.shop.onlinetrade.service.CategoryService;
import my.shop.onlinetrade.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private final CategoryService categoryService;
    private final ProductService productService;


    @Autowired
    public CatalogController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("")
    public String catalogView(@RequestParam(name = "category", required = false, defaultValue = "iPhone") String category,
                              @RequestParam(name = "model", required = false, defaultValue = "iPhone 14 Pro") String modelProduct,
                              Model model) {
        model.addAttribute("category", category);
        model.addAttribute("model", modelProduct);
        model.addAttribute("categoriesWithProductNames", productService.findCategoriesWithProductNames());
        return "catalog";
    }


    @PostMapping("/filter")
    @ResponseBody
    public Map<String, Set<String>> formComponent(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "model") String modelProduct) {
        return categoryService.getFilterableProductFeaturesMap(category, modelProduct);
    }

    @GetMapping("/category")
    @ResponseBody
    public List<Category> getAllCategory() {
        return categoryService.getAll();
    }


    @PostMapping("/model")
    @ResponseBody
    public List<String> model(@RequestParam("category") String categoryName) {
        return productService.findProductByCategory(categoryName);
    }


    @PostMapping(value = "/product/filter", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public FilterResponseDto getProductByFormParam(@RequestBody FilterRequestDto filterRequestDto) {
        return productService.searchProductsByCriteriaAndSorting(filterRequestDto);
    }

}