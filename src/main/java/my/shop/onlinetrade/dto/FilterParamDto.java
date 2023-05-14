package my.shop.onlinetrade.dto;


public class FilterParamDto {
    private String name;
    private String value;

    public FilterParamDto() {
    }

    public FilterParamDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FilterParamDto{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
