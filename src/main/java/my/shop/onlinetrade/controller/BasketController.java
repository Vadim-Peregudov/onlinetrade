package my.shop.onlinetrade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import my.shop.onlinetrade.dto.ProductSerializer;
import my.shop.onlinetrade.entity.Category;
import my.shop.onlinetrade.entity.Product;
import my.shop.onlinetrade.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<Product, Integer> getProductAndCount(@RequestBody Map<String, Integer> basket) {
        var map = productService.getProductsByIds(basket);
        return productService.getProductsByIds(basket);
    }

}
