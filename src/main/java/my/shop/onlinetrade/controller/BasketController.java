package my.shop.onlinetrade.controller;

import my.shop.onlinetrade.entity.Product;
import my.shop.onlinetrade.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private final ProductService productService;


    @Autowired
    public BasketController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/page")
    public String basketView(Model model) {
        model.addAttribute("categoriesWithProductNames", productService.findCategoriesWithProductNames());
        return "basket";
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<Product, Integer> getProductAndCount(@RequestBody Map<String, Integer> basket) {
        return productService.getProductsByIdsAndCount(basket);
    }
    /*@PostMapping(value = "/product")
    public ResponseEntity<Map<Product, Integer>> getProductAndCount(@RequestBody Map<String, Integer> basket) {
        return ResponseEntity.ok(productService.getProductsByIdsAndCount(basket));
    }*/
}
