package in.codingage.blooms;

import in.codingage.blooms.models.Blog;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.models.User;
import org.springframework.context.annotation.Configuration;

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

    //collection of user
    private List<User> userList = new ArrayList<>();

    //collection of blog
    private List<Blog> blogList = new ArrayList<>();

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }
}
