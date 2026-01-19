package in.codingage.blooms.controller;

import in.codingage.blooms.Database;
import in.codingage.blooms.dto.UserRequest;
import in.codingage.blooms.dto.UserResponse;
import in.codingage.blooms.models.User;
import in.codingage.blooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ============================ CREATE ============================
    @PostMapping
    public void createUser(@RequestBody UserRequest request) {

        // CREATE NEW USER OBJECT
        User user = new User();
        user.setId(String.valueOf(System.currentTimeMillis())); // unique id
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());  // simple plain text (later hash in real project)
        user.setRole(request.getRole());          // e.g., ADMIN / USER
        user.setActive(true);                     // active by default
        user.setCreatedBy("SYSTEM");
        user.setCreatedDTTM(new Timestamp(System.currentTimeMillis()));

        // SAVE USER
        userRepository.save(user);

        // SAVE IN IN-MEMORY DATABASE
        Database.getInstance().getUserList().add(user);
    }

    // ============================ GET BY REQUEST PARAM ============================
    @GetMapping
    public UserResponse getUserById(@RequestParam String userId) {

        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();

            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole());

            return response;
        }
        return null;
    }

    // ============================ GET BY PATH VARIABLE ============================
    @GetMapping("/{userId}")
    public UserResponse getUserByPath(@PathVariable String userId) {

        for (User user : Database.getInstance().getUserList()) {
            if (user.getId().equals(userId) && user.isActive()) {

                UserResponse response = new UserResponse();
                response.setId(user.getId());
                response.setName(user.getName());
                response.setEmail(user.getEmail());
                response.setRole(user.getRole());

                return response;
            }
        }
        return null;
    }

    // ============================ GET ALL ============================
    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {
            if (user.isActive()) {

                UserResponse response = new UserResponse();
                response.setId(user.getId());
                response.setName(user.getName());
                response.setEmail(user.getEmail());
                response.setRole(user.getRole());

                responses.add(response);
            }
        }
        return responses;
    }

    // ============================ UPDATE ============================
    @PutMapping
    public UserResponse updateUser(@RequestBody UserRequest request) {

        if (request == null || request.getId() == null) {
            return null;
        }

        Optional<User> optional = userRepository.findById(request.getId());
        if (optional.isPresent()) {

            User user = optional.get();

            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(request.getRole());

            userRepository.save(user);

            // CREATE RESPONSE
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole());

            return response;
        }
        return null;
    }

    // ============================ DELETE (SOFT DELETE) ============================
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable String id) {

        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {

            User user = optional.get();
            user.setActive(false); // IMPORTANT: soft delete
            userRepository.save(user);

            return true;
        }
        return false;
    }
}