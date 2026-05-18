package in.codingage.blooms.service;

import in.codingage.blooms.models.Blog;
import in.codingage.blooms.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return blogPage.getContent();
    }

    public Blog getBlogById(String id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }

    public Blog updateBlog(String id, Blog updatedBlog) {
        Blog existingBlog = getBlogById(id);
        existingBlog.setTitle(updatedBlog.getTitle());
        existingBlog.setContent(updatedBlog.getContent());
        existingBlog.setAuthorName(updatedBlog.getAuthorName());
        existingBlog.setCategoryId(updatedBlog.getCategoryId());
        return blogRepository.save(existingBlog);
    }

    public void deleteBlog(String id) {
        blogRepository.deleteById(id);
    }

    public List<Blog> searchByTitle(String title) {
        return blogRepository.findByTitleContaining(title);
    }

    public List<Blog> getByCategory(String categoryId) {

        return blogRepository.findByCategoryId(categoryId);
    }
}