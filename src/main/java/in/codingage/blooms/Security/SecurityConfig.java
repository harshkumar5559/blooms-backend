package in.codingage.blooms.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // ✅ Swagger URLs allow
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ✅ AI endpoint allow
                        .requestMatchers(
                                "/ai/**"
                        ).permitAll()

                        // ✅ Category APIs allow
                        .requestMatchers(
                                "/category/**"
                        ).permitAll()

                        // ✅ Blog APIs allow
                        .requestMatchers(
                                "/blog/**"
                        ).permitAll()

                        // ✅ SubCategory APIs allow
                        .requestMatchers(
                                "/subcategory/**"
                        ).permitAll()

                        // ✅ Baaki sab requests allow
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}