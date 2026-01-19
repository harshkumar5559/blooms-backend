package in.codingage.blooms.controller;

import in.codingage.blooms.Database;
import in.codingage.blooms.dto.BlogRequest;
import in.codingage.blooms.dto.BlogResponse;
import in.codingage.blooms.models.Blog;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    // ============================ CREATE ============================
    @PostMapping
    public void createBlog(@RequestBody BlogRequest request) {

        boolean categoryExists = false;
        boolean subCategoryExists = false;

        // CHECK CATEGORY EXIST & ACTIVE
        for (Category category : Database.getInstance().getCategoryList()) {
            if (category.getId().equals(request.getCategoryId()) && category.isActive()) {
                categoryExists = true;
                break;
            }
        }
        if (!categoryExists) {
            System.out.println("ERROR: Category not found");
            return;
        }

        // CHECK SUBCATEGORY EXIST & ACTIVE
        for (SubCategory sub : Database.getInstance().getSubCategoryList()) {
            if (sub.getId().equals(request.getSubCategoryId()) && sub.isActive()) {
                subCategoryExists = true;
                break;
            }
        }
        if (!subCategoryExists) {
            System.out.println("ERROR: SubCategory not found");
            return;
        }

        // CREATE BLOG OBJECT
        Blog blog = new Blog();
        blog.setId(String.valueOf(System.currentTimeMillis()));
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setAuthorName(request.getAuthorName());
        blog.setCategoryId(request.getCategoryId());
        blog.setStatus("PUBLISHED");
        blog.setActive(true);
        blog.setCreatedBy("ADMIN");
        blog.setCreatedDTTM(new Timestamp(System.currentTimeMillis()));

        // SAVE BLOG
        blogRepository.save(blog);
        Database.getInstance().getBlogList().add(blog);
    }

    // ============================ GET BY REQUEST PARAM ============================
    @GetMapping
    public BlogResponse getBlogById(@RequestParam String blogId) {

        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if (blogOptional.isPresent()) {

            Blog blog = blogOptional.get();
            BlogResponse response = new BlogResponse();

            response.setId(blog.getId());
            response.setTitle(blog.getTitle());
            response.setContent(blog.getContent());
            response.setAuthorName(blog.getAuthorName());
            response.setCategoryId(blog.getCategoryId());

            return response;
        }
        return null;
    }

    // ============================ GET BY PATH VARIABLE ============================
    @GetMapping("/{blogId}")
    public BlogResponse getBlogByPath(@PathVariable String blogId) {

        for (Blog blog : Database.getInstance().getBlogList()) {
            if (blog.getId().equals(blogId) && blog.isActive()) {

                BlogResponse response = new BlogResponse();
                response.setId(blog.getId());
                response.setTitle(blog.getTitle());
                response.setContent(blog.getContent());
                response.setAuthorName(blog.getAuthorName());
                response.setCategoryId(blog.getCategoryId());

                return response;
            }
        }
        return null;
    }

    // ============================ GET ALL ============================
    @GetMapping("/all")
    public List<BlogResponse> getAllBlogs() {

        List<Blog> blogs = blogRepository.findAll();
        List<BlogResponse> responses = new ArrayList<>();

        for (Blog blog : blogs) {
            if (blog.isActive()) {

                BlogResponse response = new BlogResponse();
                response.setId(blog.getId());
                response.setTitle(blog.getTitle());
                response.setContent(blog.getContent());
                response.setAuthorName(blog.getAuthorName());
                response.setCategoryId(blog.getCategoryId());

                responses.add(response);
            }
        }
        return responses;
    }

    // ============================ UPDATE ============================
    @PutMapping
    public BlogResponse updateBlog(@RequestBody BlogRequest request) {

        if (request == null || request.getId() == null) {
            return null;
        }

        Optional<Blog> blogOptional = blogRepository.findById(request.getId());
        if (blogOptional.isPresent()) {

            Blog blog = blogOptional.get();

            blog.setTitle(request.getTitle());
            blog.setContent(request.getContent());
            blog.setAuthorName(request.getAuthorName());
            blog.setCategoryId(request.getCategoryId());


            blogRepository.save(blog);

            // RESPONSE OBJECT
            BlogResponse response = new BlogResponse();
            response.setId(blog.getId());
            response.setTitle(blog.getTitle());
            response.setContent(blog.getContent());
            response.setAuthorName(blog.getAuthorName());
            response.setCategoryId(blog.getCategoryId());

            return response;
        }
        return null;
    }

    // ============================ DELETE (SOFT DELETE) ============================
    @DeleteMapping("/{id}")
    public boolean deleteBlog(@PathVariable String id) {

        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {

            Blog blog = blogOptional.get();
            blog.setActive(false); // IMPORTANT: soft delete
            blogRepository.save(blog);
            return true;
        }
        return false;
    }
}