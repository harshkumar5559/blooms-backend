package in.codingage.blooms.repository;


import in.codingage.blooms.models.SubCategory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  SubCategoryRepository extends MongoRepository<SubCategory, String> {

    List<SubCategory> findByCategoryId(String categoryId);

}