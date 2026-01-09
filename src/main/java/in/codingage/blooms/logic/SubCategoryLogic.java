package in.codingage.blooms.logic;

import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryLogic {

    private List<SubCategory> subCategories = new ArrayList<>();

    public List<SubCategory> getSubCategories() {
        createSubCategory();
        return subCategories;
    }

    // createSubCategory
    public void createSubCategory() {
        List<Category> categories = new CategoryLogic().getCategories();

        for (Category category : categories){
            SubCategory subCategory = new SubCategory();
            subCategory.setId(category.getId() + "_subA");
            subCategory.setCategoryId(category.getId());
            subCategory.setName("SubCategory A of " + category.getName());
            subCategories.add(subCategory);
            SubCategory subCategoryA = new SubCategory();
            subCategoryA.setId(category.getId() + "_subB");
            subCategory.setCategoryId(category.getId());
            subCategory.setName("SubCategory  B of " + category.getName());
            subCategories.add(subCategoryA);
            SubCategory subCategoryB = new SubCategory();
            subCategoryB.setId(category.getId() + "_subC");
            subCategory.setCategoryId(category.getId());
            subCategory.setName("SubCategory  C of " + category.getName());
            subCategories.add(subCategoryB);
        }
    }
}
