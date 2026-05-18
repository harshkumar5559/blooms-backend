package in.codingage.blooms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.codingage.blooms.models.Blog;
import in.codingage.blooms.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder; // 👈 सिक्योरिटी से यूजर निकालने के लिए
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blog")
@CrossOrigin(origins = "*")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository; // 👈 तुम्हारी असली रिपोजिटरी यहाँ इंजेक्ट हो गई

    // ==========================================
    // 1. CREATE (इमेज, टेक्स्ट और लॉग-इन यूजर के साथ ब्लॉग सेव करना)
    // ==========================================
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createBlog(
            @RequestPart("image") MultipartFile file,
            @RequestPart("blogData") String blogDataJson
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Blog blog = objectMapper.readValue(blogDataJson, Blog.class);

            // 📸 फ्रंटएंड से आई हुई इमेज का नाम सेट करना
            if (file != null && !file.isEmpty()) {
                String imageName = file.getOriginalFilename();
                blog.setImageUrl(imageName);
                System.out.println("फोटो मिल गई भाई: " + imageName);
            }

            // 👤 SECURITY CONNECTOR: लॉग-इन यूजर का नाम टोकन से निकालना
            String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
            blog.setAuthorName(loggedInUser); // ⚠️ पक्का करना तुम्हारी Blog Class में authorName या userId का वेरिएबल हो

            // 💾 असली डेटाबेस में सेव करना
            Blog savedBlog = blogRepository.save(blog);

            return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error saving blog in Database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==========================================
    // 2. READ ALL (सारे ब्लॉग्स डेटाबेस से लाना)
    // ==========================================
    @GetMapping("/all")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll(); // 👈 डेटाबेस से सारे ब्लॉग्स निकाले
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // ==========================================
    // 3. READ BY ID (कोई एक सिंगल ब्लॉग ढूंढना)
    // ==========================================
    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable String id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (blog.isPresent()) {
            return new ResponseEntity<>(blog.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Blog Not Found भाई!", HttpStatus.NOT_FOUND);
    }

    // ==========================================
    // 4. UPDATE (ब्लॉग को एडिट करना)
    // ==========================================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable String id, @RequestBody Blog updatedBlog) {
        Optional<Blog> existingBlogOpt = blogRepository.findById(id);

        if (existingBlogOpt.isPresent()) {
            Blog existingBlog = existingBlogOpt.get();

            // 🔒 सिक्योरिटी चेक: क्या एडिट करने वाला वही है जिसने ब्लॉग लिखा था?
            String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!existingBlog.getAuthorName().equals(loggedInUser)) {
                return new ResponseEntity<>("You can only edit your own blogs!", HttpStatus.FORBIDDEN);
            }

            existingBlog.setTitle(updatedBlog.getTitle());
            existingBlog.setContent(updatedBlog.getContent());
            existingBlog.setSubCategoryId(updatedBlog.getSubCategoryId());

            blogRepository.save(existingBlog); // डेटाबेस में अपडेट सेव किया
            return new ResponseEntity<>("Blog Updated Successfully! 🔄", HttpStatus.OK);
        }
        return new ResponseEntity<>("Blog Not Found to Update!", HttpStatus.NOT_FOUND);
    }

    // ==========================================
    // 5. DELETE (सिर्फ अपना ब्लॉग डिलीट करने की परमिशन)
    // ==========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable String id) {
        Optional<Blog> blogOpt = blogRepository.findById(id);

        if (blogOpt.isPresent()) {
            Blog blog = blogOpt.get();

            // 🔒 SECURITY CHECK: टोकन वाले यूजर का नाम और ब्लॉग के ऑथर का नाम मैच करना
            String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

            if (blog.getAuthorName().equals(loggedInUser)) {
                blogRepository.deleteById(id); // 🗑️ सिर्फ मैच होने पर ही डिलीट होगा
                return new ResponseEntity<>("Blog Deleted Successfully! 🗑️", HttpStatus.OK);
            } else {
                // अगर कोई दूसरा डिलीट करने की कोशिश करे
                return new ResponseEntity<>("Unauthorized! You can only delete your own blogs भाई!", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("Blog Not Found to Delete!", HttpStatus.NOT_FOUND);
    }
}