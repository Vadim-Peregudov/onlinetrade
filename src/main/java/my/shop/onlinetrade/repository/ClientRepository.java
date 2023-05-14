package my.shop.onlinetrade.repository;

import my.shop.onlinetrade.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Customers,Long> {
}
