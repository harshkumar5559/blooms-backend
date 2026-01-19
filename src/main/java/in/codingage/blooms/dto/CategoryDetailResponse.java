package in.codingage.blooms.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import in.codingage.blooms.models.SubCategory;

import java.util.List;
public class CategoryDetailResponse {

    private String categoryId;
    private String name;

    private List<SubCategory>subCategoryDetailList;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubCategoryDetailList() {
        return subCategoryDetailList;
    }

    public void setSubCategoryDetailList(List<SubCategory> subCategoryDetailList) {
        this.subCategoryDetailList = subCategoryDetailList;
    }
}
