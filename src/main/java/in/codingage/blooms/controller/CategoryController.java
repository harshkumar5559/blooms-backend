package in.codingage.blooms.controller;

import in.codingage.blooms.Database;
import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    // CRUD - Create, Read, Update , Delete

//    Supplier<Integer> capacitySupplier = () -> (int) (Math.random() * 10) + 1;

    public void createCategory(CategoryRequest categoryRequest){
        Category category = new Category();
        // coming from ui request
        category.setName(categoryRequest.getTitle());
        category.setDescription(categoryRequest.getDesc());
        category.setImageUrl(categoryRequest.getcUrl());

        // for now lets' give this access only to admins
        category.setStatus(Status.PUBLISHED.getDisplayName());
        category.setId(String.valueOf(System.currentTimeMillis()));

        category.setCreatedBy("ADMIN");

        category.setActive(true);

        category.setCreatedDTTM(LocalDateTime.now());

        // local db - item save
        Database database = Database.getInstance();
        database.getCategoryList().add(category);
    }

    public CategoryResponse getCategory(String categoryId){
        List<Category> categoryList = Database.getInstance().getCategoryList();
        for(Category category : categoryList){
            if(category.getId().equals(categoryId)){
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setcUrl(category.getImageUrl());
                categoryResponse.setId(category.getId());
                categoryResponse.setTitle(category.getName());
                categoryResponse.setDesc(category.getDescription());
                return categoryResponse;
            }
        }
        return null;
    }

    public List<CategoryResponse> getCategories(){
        List<Category> categoryList = Database.getInstance().getCategoryList();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : categoryList) {
            if (category.isActive()) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setcUrl(category.getImageUrl());
                categoryResponse.setId(category.getId());
                categoryResponse.setTitle(category.getName());
                categoryResponse.setDesc(category.getDescription());
                categoryResponses.add(categoryResponse);
            }
        }
        return categoryResponses;
        }

     public boolean deleteCategory(String categoryId){
        // iterate the list that comes from your database and set the active flag to false
         // return true
         // if id not found, return false

         List<Category> categoryList = Database.getInstance().getCategoryList();

         for(Category cat : categoryList){
             if(cat.getId().equals(categoryId)){
                 // hard delete
//                 categoryList.remove(cat)
                 // soft delete [db hai, you have status field which is inactive]
                 cat.setActive(false);
                 return true;
             }
         }
         return false;
     }

     public CategoryResponse updateCategory(CategoryRequest categoryRequest){
        // fetch category by id and update its name, desc and curl using category request
         // validation to return from here only if id is not present
         CategoryResponse categoryResponse = new CategoryResponse();
         if(categoryRequest.getId() == null){
             // we will send error to UI later
             return categoryResponse;
         }
         List<Category> categoryResponses = Database.getInstance().getCategoryList();

         for(Category category : categoryResponses){
             if(category.getId().equals(categoryRequest.getId())){
                 category.setName(categoryRequest.getTitle());
                 category.setDescription(categoryRequest.getDesc());
                 category.setImageUrl(categoryRequest.getcUrl());
                 // create a category response object
                 categoryResponse.setDesc(category.getDescription());
                 categoryResponse.setId(category.getId());
                 categoryResponse.setcUrl(category.getImageUrl());
                 categoryResponse.setTitle(category.getName());
                 return categoryResponse;
             }
         }
         return categoryResponse;
     }
    }




