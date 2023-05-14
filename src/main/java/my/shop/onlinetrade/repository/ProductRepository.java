package my.shop.onlinetrade.repository;

import my.shop.onlinetrade.entity.Product;
import my.shop.onlinetrade.repository.custom.CustomProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {

    @Query("SELECT DISTINCT p.category.name, p.name FROM Product p JOIN p.category")
    List<Object[]> findCategoriesWithProductNames();

    @Query("SELECT DISTINCT p.name FROM Category c JOIN c.products p WHERE c.name = :categoryName")
    List<String> findDistinctByNameByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT f.name , p.id , pf.value from Product p join p.category c join p.productFeatures pf join pf.features f " +
            "where p.name = :productName " +
            "and c.name = :categoryName " +
            "AND f.isFilterable " +
            "AND p.id <> :productId " +
            "AND pf.value NOT IN (select pf.value from ProductFeatures pf where pf.product.id = :productId)")
    List<Object[]> findNonMatchingProductFeatures(@Param("categoryName") String categoryName,
                                                  @Param("productName") String productName,
                                                  @Param("productId") Long productId);

    @Query("SELECT p FROM Product p JOIN p.productImages WHERE p.id IN :ids")
    List<Product> findProductsByIds (@Param("ids") List<Long> productsId);


}
