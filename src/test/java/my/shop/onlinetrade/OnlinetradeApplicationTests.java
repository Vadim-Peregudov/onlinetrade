package my.shop.onlinetrade;

import my.shop.onlinetrade.entity.*;
import my.shop.onlinetrade.repository.*;
import my.shop.onlinetrade.security.RegisterAdminRequest;
import my.shop.onlinetrade.service.AuthenticationService;
import my.shop.onlinetrade.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class OnlinetradeApplicationTests {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductImageRepository productImageRepository;
    @Autowired
    ProductFeaturesRepository productFeaturesRepository;
    @Autowired
    FeaturesRepository featuresRepository;
    @Autowired
    ProductService productService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
/*
        Category category1 = new Category("iPhone");
        Category category2 = new Category("Гаджеты");
        categoryRepository.saveAll(List.of(category1, category2));

        Features features1 = new Features("Цвет", true);
        Features features2 = new Features("Обьем памяти", true);
        Features features3 = new Features("Размер", false);
        Features features4 = new Features("Тип устройства", false);

        featuresRepository.saveAll(List.of(features1, features2, features3, features4));

        Product product1 = new Product("iPhone 14", "Apple iPhone 14 Pro 128Gb Темно-фиолетовый", "Test 1", 95000_00L, 10, category1);
        Product product2 = new Product("iPhone 14", "Apple iPhone 14 Pro 128Gb Золотой", "Test 2", 95000_00L, 100, category1);
        Product product3 = new Product("Sony", "Sony PlayStation 5 Digital Version", "test", 470000_00L, 2, category2);
        Product product4 = new Product("Sony", "Sony PlayStation VR2 Белый", "test2", 550000_00L, 2, category2);

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        ProductFeatures productFeatures1 = new ProductFeatures(product1, features1, "Темно-фиолетовый");
        ProductFeatures productFeatures2 = new ProductFeatures(product1, features2, "128 Гб");
        ProductFeatures productFeatures3 = new ProductFeatures(product1, features3, "150/150/150");

        ProductFeatures productFeatures4 = new ProductFeatures(product2, features1, "Золотой");
        ProductFeatures productFeatures5 = new ProductFeatures(product2, features2, "128 Гб");
        ProductFeatures productFeatures6 = new ProductFeatures(product2, features3, "150/150/150");

        ProductFeatures productFeatures7 = new ProductFeatures(product3, features4, "Игровая консоль");
        ProductFeatures productFeatures8 = new ProductFeatures(product4, features4, "Шлем VR");

        productFeaturesRepository.saveAll(List.of(productFeatures1, productFeatures2, productFeatures3, productFeatures4, productFeatures5, productFeatures6));
        productFeaturesRepository.saveAll(List.of(productFeatures7, productFeatures8));

        ProductImage productImage1 = new ProductImage(product1, "https://i-point.ru/storage/gallery/big/2707.jpg");
        ProductImage productImage11 = new ProductImage(product1, "https://i-point.ru/storage/gallery/big/2707.jpg");
        ProductImage productImage111 = new ProductImage(product1, "https://i-point.ru/storage/gallery/big/2707.jpg");
        ProductImage productImage1111 = new ProductImage(product1, "https://i-point.ru/storage/gallery/big/2707.jpg");
        ProductImage productImage2 = new ProductImage(product2, "https://i-point.ru/storage/gallery/big/2704.jpg");
        ProductImage productImage3 = new ProductImage(product3, "https://i-point.ru/storage/gallery/big/2124.jpg");
        ProductImage productImage4 = new ProductImage(product4, "https://i-point.ru/storage/gallery/big/3221.jpg");

        productImageRepository.saveAll(List.of(productImage1, productImage2));
        productImageRepository.saveAll(List.of(productImage3, productImage4));
        productImageRepository.saveAll(List.of(productImage11, productImage111, productImage1111));
*//*
        FilterRequestDto filterRequestDto = new FilterRequestDto("Гаджеты", "Sony", new ArrayList<>(), 1, "ALL");
        var x = productService.searchProductsByCriteriaAndSorting(filterRequestDto);
        System.out.println(x);
*/
/*        Product product = productService.findProductById(1);
        System.out.println(productService.getCharacteristicsToProductsMapByCategory(product));

 */
        System.out.println(passwordEncoder.encode("1234"));
    }

}
