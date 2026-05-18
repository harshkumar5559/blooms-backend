package in.codingage.blooms.controller;

import in.codingage.blooms.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


        @PostMapping("/login")
        public String login(@RequestBody User user) {
            if(user.getUsername().equals("admin") && user.getPassword().equals("admin")) {
                return "TOKEN_GENERATE_HOGA";
            }
            return "Invalid credentials";
        }

}
