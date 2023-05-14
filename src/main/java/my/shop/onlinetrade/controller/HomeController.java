package my.shop.onlinetrade.controller;

import my.shop.onlinetrade.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public String indexView(Model model) {
        model.addAttribute("categoriesWithProductNames", productService.findCategoriesWithProductNames());
        return "index";
    }
}
