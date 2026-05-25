package in.codingage.blooms.controller;

import in.codingage.blooms.models.User;
import in.codingage.blooms.repository.UserRepository; // अपने सही रिपोजिटरी पैकेज का नाम देखना भाई
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth") // यानी रास्ता बनेगा: /auth/register और /auth/login
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 📝 1. नया अकाउंट बनाने का असली मेथड
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // चेक करो कि क्या ईमेल पहले से रजिस्टर तो नहीं है
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("ईमेल पहले से मौजूद है भाई!");
        }

        // अगर 'username' फील्ड खाली आ रही है तो नाम को ही यूजरनेम बना दो
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            user.setUsername(user.getName());
        }

        userRepository.save(user);
        return ResponseEntity.ok().body("{\"message\": \"Success\"}");
    }

    // 🔑 2. डेटाबेस से लॉगिन करने का मेथड
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        // डेटाबेस में ईमेल से यूजर को ढूंढो
        Optional<User> userOpt = userRepository.findByEmail(loginDetails.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // पासवर्ड मैच करो (अभी बिना एन्क्रिप्शन के सिंपल चेक)
            if (user.getPassword().equals(loginDetails.getPassword())) {
                // फ्रंटएंड के लिए एक नकली JWT टोकन और यूजर का नाम रिटर्न कर रहे हैं
                return ResponseEntity.ok().body("{" +
                        "\"token\": \"MOCK_JWT_TOKEN_FOR_BLOOMS_APP\"," +
                        "\"username\": \"" + user.getUsername() + "\"," +
                        "\"email\": \"" + user.getEmail() + "\"" +
                        "}");
            }
        }
        return ResponseEntity.badRequest().body("गलत ईमेल या पासवर्ड भाई!");
    }
}