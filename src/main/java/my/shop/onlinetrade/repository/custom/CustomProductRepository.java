package my.shop.onlinetrade.repository.custom;

import my.shop.onlinetrade.dto.FilterRequestDto;
import my.shop.onlinetrade.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CustomProductRepository {
    Page<Product> searchProducts(FilterRequestDto filterRequestDto, int pageSize);
}
