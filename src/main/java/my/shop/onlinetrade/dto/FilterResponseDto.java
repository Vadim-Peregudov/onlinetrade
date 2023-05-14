package my.shop.onlinetrade.dto;

import my.shop.onlinetrade.entity.Product;

import java.util.List;

public class FilterResponseDto {
    private List<Product> productList;
    private long totalPage;
    private long page;

    public FilterResponseDto() {
    }

    public FilterResponseDto(List<Product> productList, Long totalPage, Long page) {
        this.productList = productList;
        this.totalPage = totalPage;
        this.page = page;

    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "FilterResponseDto{" +
                "productList=" + productList +
                ", totalPage=" + totalPage +
                ", page=" + page +
                '}';
    }
}
