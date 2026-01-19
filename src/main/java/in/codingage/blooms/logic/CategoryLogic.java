package in.codingage.blooms.logic;

import in.codingage.blooms.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryLogic {

    private List<Category> categories = new ArrayList<>();

    public List<Category> getCategories() {
        createCategory();
        return categories;
    }

    // create category


    public void createCategory() {
        Category category = new Category();
        category.setName("Technology");
        category.setId("cat001");
        categories.add(category);
        Category categoryB = new Category();
        category.setName("Health");
        category.setId("cat002");
        categories.add(category);
        Category categoryC = new Category();
        category.setName("Politics");
        category.setId("cat003");
        categories.add(category);
    }



}