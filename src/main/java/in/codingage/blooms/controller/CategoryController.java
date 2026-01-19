package in.codingage.blooms.controller;

import in.codingage.blooms.Database;
import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public void createCategory(@RequestBody CategoryRequest request) {

        Category category = new Category();
        category.setId(String.valueOf(System.currentTimeMillis()));
        category.setName(request.getTitle());
        category.setDescription(request.getDesc());
        category.setImageUrl(request.getcUrl());
        category.setStatus("PUBLISHED");
        category.setActive(true);
        category.setCreatedBy("ADMIN");
        category.setCreatedDTTM(LocalDateTime.now());

        Database database=Database.getInstance();

        //persist category object to database
        categoryRepository.save(category);

        database.getCategoryList().add(category);
    }

    @GetMapping
    public CategoryResponse getCategoryById(@RequestParam String categoryId) {
        //List<Category>categoryList=categoryRepository.findAll();

        Optional<Category>  Category=categoryRepository.findById(categoryId);
        if(Category.isPresent()){
            CategoryResponse response = new CategoryResponse();
            response.setId(Category.get().getId());
            response.setcUrl(Category.get().getImageUrl());
            response.setDesc(Category.get().getDescription());
            response.setTitle(Category.get().getName());

            return response;

        }
        return null;
    }
    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryByPathParam(@PathVariable(value = "categoryId") String categoryId) {
       List<Category>categoryList=Database.getInstance().getCategoryList();
       for(Category category:categoryList){
           if(category.getId().equals(categoryId)){
               CategoryResponse response=new CategoryResponse();
               response.setId(category.getId());
               response.setcUrl(category.getImageUrl());
               response.setDesc(category.getDescription());
               response.setTitle(category.getName());

               return response;
           }
       }
       return null;
    }


    @GetMapping("/all")
    public List<CategoryResponse> getAllCategories() {
     //   List<Category>categories=Database.getInstance().getCategoryList();
        List<Category>categories=categoryRepository.findAll();

        List<CategoryResponse> responses = new ArrayList<>();

        for (Category category : categories) {
            if (category.isActive()) {

                CategoryResponse response = new CategoryResponse();
                response.setId(category.getId());
                response.setTitle(category.getName());
                response.setDesc(category.getDescription());
                response.setcUrl(category.getImageUrl());

                responses.add(response);
            }
        }
        return responses;
    }

    @DeleteMapping("/{id}")
    public boolean deleteCategory(@PathVariable String id) {
        Optional<Category>categoryOptional=categoryRepository.findById(id);
        if(categoryOptional.isPresent()){
            Category category=categoryOptional.get();
            category.setActive(false);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @PutMapping
    public CategoryResponse updateCategory(@RequestBody CategoryRequest request) {

        if (request ==null || request.getId() == null) {
            return null;
        }

        Optional<Category>categoryOptional=categoryRepository.findById(request.getId());
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();

            category.setName(request.getTitle());
            category.setDescription(request.getDesc());
            category.setImageUrl(request.getcUrl());


            //create a category response object.........
            CategoryResponse response = new CategoryResponse();
            response.setId(category.getId());
            response.setTitle(category.getName());
            response.setDesc(category.getDescription());
            response.setcUrl(category.getImageUrl());

            return response;

        }
        return null;
    }
}
