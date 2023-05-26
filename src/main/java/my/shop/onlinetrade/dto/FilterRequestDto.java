package my.shop.onlinetrade.dto;

import java.util.ArrayList;
import java.util.List;

public class FilterRequestDto {
    private String categoryName;
    private String modelName;
    private List<FilterParamDto> filterParam = new ArrayList<>();
    private int page;
    private String sort;

    public FilterRequestDto() {
    }

    public FilterRequestDto(String categoryName, String modelName, List<FilterParamDto> filterParam, int page, String sort) {
        this.categoryName = categoryName;
        this.modelName = modelName;
        this.filterParam = filterParam;
        this.page = page;
        this.sort = sort;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public List<FilterParamDto> getFilterParam() {
        return filterParam;
    }

    public void setFilterParam(List<FilterParamDto> filterParam) {
        this.filterParam = filterParam;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "FilterRequestDto{" +
                "categoryName='" + categoryName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", filterParams=" + filterParam +
                ", page=" + page +
                ", sort='" + sort + '\'' +
                '}';
    }


}
