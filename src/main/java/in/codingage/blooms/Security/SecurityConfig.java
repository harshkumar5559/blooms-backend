package in.codingage.blooms.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🌟 यही वो जादुई लाइनें हैं जो ब्राउज़र के 'Failed to fetch' को जड़ से खत्म करेंगी भाई
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*")); // हर जगह के फ्रंटएंड को अंदर आने दो
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                }))

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