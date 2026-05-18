package in.codingage.blooms.repository;

import in.codingage.blooms.models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {

    // ✅ स्प्रिंग बूट खुद इसकी क्वेरी बनाएगा, static पूरी तरह हटा दिया
    List<Blog> findByTitleContaining(String title);

    List<Blog> findByCategoryId(String categoryId);

    Page<Blog> findAll(Pageable pageable);
}