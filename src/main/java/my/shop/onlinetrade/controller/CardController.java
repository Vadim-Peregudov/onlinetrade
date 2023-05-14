package my.shop.onlinetrade.controller;

import my.shop.onlinetrade.entity.Product;
import my.shop.onlinetrade.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class CardController {

    private final ProductService productService;

    @Autowired
    public CardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String cardView(@PathVariable("id") Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("productFeaturesMap", productService.getCharacteristicsToProductsMapByCategory(product));
        model.addAttribute("product", product);
        model.addAttribute("categoriesWithProductNames", productService.findCategoriesWithProductNames());
        return "card";
    }
}
