package in.codingage.blooms.logic;

import in.codingage.blooms.models.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryLogic {

    private List<Category> categories = new ArrayList<>();

    public List<Category> getCategories() {
        if(categories.isEmpty()) { // ताकि बार-बार लिस्ट डुप्लिकेट न हो
            createCategory();
        }
        return categories;
    }

    public void createCategory() {
        // ✅ पहली कैटेगरी
        Category category = new Category();
        category.setName("Technology");
        category.setId("cat001");
        categories.add(category);

        // ✅ दूसरी कैटेगरी (categoryB वेरिएबल का सही इस्तेमाल)
        Category categoryB = new Category();
        categoryB.setName("Health");
        categoryB.setId("cat002");
        categories.add(categoryB);

        // ✅ तीसरी कैटेगरी (categoryC वेरिएबल का सही इस्तेमाल)
        Category categoryC = new Category();
        categoryC.setName("Politics");
        categoryC.setId("cat003");
        categories.add(categoryC);
    }
}