package in.codingage.blooms.models;

import java.util.List;

public class CategoryMapping {
    private String categoryId;
    private List<String> subCategoryIds;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getSubCategoryIds() {
        return subCategoryIds;
    }

    public void setSubCategoryIds(List<String> subCategoryIds) {
        this.subCategoryIds = subCategoryIds;
    }
}
