package in.codingage.blooms.controller;

import in.codingage.blooms.Database;
import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.dto.SubCategoryResponse;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    // ============================ CREATE ============================
    @PostMapping
    public void createSubCategory(@RequestBody SubCategoryRequest request) {

        boolean categoryExists = false;

        // IMPORTANT: check parent category exist & active
        for (Category category : Database.getInstance().getCategoryList()) {
            if (category.getId().equals(request.getCategoryId())
                    && category.isActive()) {
                categoryExists = true;
                break;
            }
        }

        // agar category galat hai to subcategory create nahi hogi
        if (!categoryExists) {
            System.out.println("ERROR: Category not found");
            return;
        }

        // create subcategory object
        SubCategory sub = new SubCategory();
        sub.setId(String.valueOf(System.currentTimeMillis()));
        sub.setName(request.getName());
        sub.setDescription(request.getDesc());
        sub.setCategoryId(request.getCategoryId());
        sub.setStatus("PUBLISHED");
        sub.setActive(true);
        sub.setCreatedBy("ADMIN");
        sub.setCreatedDTTM(new Timestamp(System.currentTimeMillis()));

        // save in DB
        subCategoryRepository.save(sub);

        // save in in-memory database
        Database.getInstance().getSubCategoryList().add(sub);
    }

    // ============================ GET BY ID (RequestParam) ============================
    @GetMapping
    public SubCategoryResponse getSubCategoryById(@RequestParam String subCategoryId) {

        Optional<SubCategory> optional = subCategoryRepository.findById(subCategoryId);

        if (optional.isPresent()) {
            SubCategory sub = optional.get();

            SubCategoryResponse response = new SubCategoryResponse();
            response.setId(sub.getId());
            response.setName(sub.getName());
            response.setDesc(sub.getDescription());
            response.setCategoryId(sub.getCategoryId());

            return response;
        }
        return null;
    }

    // ============================ GET BY ID (PathVariable) ============================
    @GetMapping("/{subCategoryId}")
    public SubCategoryResponse getSubCategoryByPath(@PathVariable String subCategoryId) {

        List<SubCategory> list = Database.getInstance().getSubCategoryList();

        for (SubCategory sub : list) {
            if (sub.getId().equals(subCategoryId)) {

                SubCategoryResponse response = new SubCategoryResponse();
                response.setId(sub.getId());
                response.setName(sub.getName());
                response.setDesc(sub.getDescription());
                response.setCategoryId(sub.getCategoryId());

                return response;
            }
        }
        return null;
    }

    // ============================ GET ALL ============================
    @GetMapping("/all")
    public List<SubCategoryResponse> getAllSubCategories() {

        List<SubCategory> subCategories = subCategoryRepository.findAll();
        List<SubCategoryResponse> responses = new ArrayList<>();

        for (SubCategory sub : subCategories) {
            if (sub.isActive()) {

                SubCategoryResponse response = new SubCategoryResponse();
                response.setId(sub.getId());
                response.setName(sub.getName());
                response.setDesc(sub.getDescription());
                response.setCategoryId(sub.getCategoryId());

                responses.add(response);
            }
        }
        return responses;
    }

    // ============================ DELETE (SOFT DELETE) ============================
    @DeleteMapping("/{id}")
    public boolean deleteSubCategory(@PathVariable String id) {

        Optional<SubCategory> optional = subCategoryRepository.findById(id);

        if (optional.isPresent()) {
            SubCategory sub = optional.get();
            sub.setActive(false);   // IMPORTANT: soft delete
            subCategoryRepository.save(sub);
            return true;
        }
        return false;
    }

    // ============================ UPDATE ============================
    @PutMapping
    public SubCategoryResponse updateSubCategory(@RequestBody SubCategoryRequest request) {

        // IMPORTANT validation
        if (request == null || request.getId() == null) {
            return null;
        }

        Optional<SubCategory> optional = subCategoryRepository.findById(request.getId());

        if (optional.isPresent()) {
            SubCategory sub = optional.get();

            sub.setName(request.getName());
            sub.setDescription(request.getDesc());

            // create response object
            SubCategoryResponse response = new SubCategoryResponse();
            response.setId(sub.getId());
            response.setName(sub.getName());
            response.setDesc(sub.getDescription());
            response.setCategoryId(sub.getCategoryId());

            return response;
        }
        return null;
    }
}