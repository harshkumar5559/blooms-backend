package in.codingage.blooms.service;

import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    // Create
    public SubCategory createSubCategory(SubCategory subCategory){

        return subCategoryRepository.save(subCategory);
    }

    //get All
    public List<SubCategory> getAll(){
        return subCategoryRepository.findAll();
    }



    //Get subcategories By Category Id
    public List<SubCategory> getByCategoryId(String categoryId){
        return
                subCategoryRepository.findByCategoryId(categoryId);
    }

    //update
    public SubCategory updateSubCategory(String id,SubCategory updatedSubcategory){
        Optional<SubCategory>optionalSubCategory=subCategoryRepository.findById(id);
        if(optionalSubCategory.isPresent()){
            SubCategory existingSubCategory=optionalSubCategory.get();
            existingSubCategory.setName(updatedSubcategory.getName());
            existingSubCategory.setCategoryId(updatedSubcategory.getCategoryId());
            return subCategoryRepository.save(existingSubCategory);
        }
        return null;

    }

    //Delete
    public boolean deleteSubCategory(String id){
        if(subCategoryRepository.existsById(id)){
            subCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
