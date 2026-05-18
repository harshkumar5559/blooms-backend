package in.codingage.blooms.service;

import in.codingage.blooms.models.User;
import in.codingage.blooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create User
    public User createUser(User user) {

        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // Get User By Id
    public User getUserById(String id) {

        Optional<User> optionalUser =
                userRepository.findById(id);

        return optionalUser.orElse(null);
    }

    // Update User
    public User updateUser(String id, User updatedUser) {

        Optional<User> optionalUser =
                userRepository.findById(id);

        if (optionalUser.isPresent()) {

            User existingUser = optionalUser.get();

            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setStatus(updatedUser.getStatus());

            return userRepository.save(existingUser);
        }

        return null;
    }

    // Delete User
    public String deleteUser(String id) {

        userRepository.deleteById(id);

        return "User deleted successfully";
    }
}