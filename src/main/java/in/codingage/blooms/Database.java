package in.codingage.blooms;

import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class Database {

    // singelton design pattern
    private static Database instance = null;

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Collection of Category
    private List<Category> categoryList = new ArrayList<>();

    // Collection of SubCategory
    private List<SubCategory> subCategoryList = new ArrayList<>();

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }
}
