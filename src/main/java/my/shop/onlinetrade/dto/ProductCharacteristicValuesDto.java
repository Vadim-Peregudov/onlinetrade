package my.shop.onlinetrade.dto;

public class ProductCharacteristicValuesDto {
    private String value;
    private Long productId;

    public ProductCharacteristicValuesDto() {
    }

    public ProductCharacteristicValuesDto(String value, Long productId) {
        this.value = value;
        this.productId = productId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCharacteristicValuesDto that = (ProductCharacteristicValuesDto) o;

        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        return getProductId() != null ? getProductId().equals(that.getProductId()) : that.getProductId() == null;
    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getProductId() != null ? getProductId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductCharacteristicValuesDto{" +
                "value='" + value + '\'' +
                ", productId=" + productId +
                '}';
    }
}
