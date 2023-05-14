package my.shop.onlinetrade.repository;

import my.shop.onlinetrade.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c.name , p.name, f.name, pf.value from Category c join c.products p join p.productFeatures pf join pf.features f " +
            "where f.isFilterable AND p.name = :productName AND c.name = :categoryName")
    List<Object[]> findFilterableProductFeatures(@Param("categoryName") String categoryName,
                                                 @Param("productName") String productName);




}
