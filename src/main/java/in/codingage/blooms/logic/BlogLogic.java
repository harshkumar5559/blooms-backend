package in.codingage.blooms.logic;

import in.codingage.blooms.models.Blog;
import in.codingage.blooms.models.CategoryMapping;

import java.util.ArrayList;
import java.util.List;

public class BlogLogic {

    public static void main(String[] args) {
        BlogLogic blogLogic = new BlogLogic();
        blogLogic.createBlog();
    }

    // createBlog

    public void createBlog() {
        Blog blog = new Blog();
        blog.setAuthorId("author001");
        blog.setTitle("AI Impacts and Future");
        blog.setDescription("AI is transforming the world...");

        List<CategoryMapping> categoryMappings = new ArrayList<>();

        CategoryMapping categoryMapping = new CategoryMapping();
        categoryMapping.setCategoryId("cat001");

        List<String> subCategoryIds = new ArrayList<>();
        subCategoryIds.add("cat001_subA");
        subCategoryIds.add("cat001_subB");

        categoryMapping.setSubCategoryIds(subCategoryIds);

        categoryMappings.add(categoryMapping);

        CategoryMapping categoryMappingB = new CategoryMapping();
        categoryMappingB.setCategoryId("cat002");

        List<String> subCategoryIdsB = new ArrayList<>();
        subCategoryIdsB.add("cat002_subA");
        subCategoryIdsB.add("cat002_subB");

        categoryMappingB.setSubCategoryIds(subCategoryIdsB);

        categoryMappings.add(categoryMappingB);

        CategoryMapping categoryMappingC = new CategoryMapping();
        categoryMappingC.setCategoryId("cat003");

        List<String> subCategoryIdsC = new ArrayList<>();
        subCategoryIdsC.add("cat003_subA");
        subCategoryIdsC.add("cat003_subB");

        categoryMappingC.setSubCategoryIds(subCategoryIdsC);

        categoryMappings.add(categoryMappingC);


        blog.setCategoryMappings(categoryMappings);


        for(CategoryMapping cm : blog.getCategoryMappings()) {
            System.out.println("Category ID: " + cm.getCategoryId());
            for (String subCatId : cm.getSubCategoryIds()) {
                System.out.println(" - SubCategory ID: " + subCatId);
            }
        }


        ///
    }
}
