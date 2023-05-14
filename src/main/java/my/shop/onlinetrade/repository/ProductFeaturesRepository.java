package my.shop.onlinetrade.repository;

import my.shop.onlinetrade.entity.ProductFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFeaturesRepository extends JpaRepository<ProductFeatures,Long> {
}
