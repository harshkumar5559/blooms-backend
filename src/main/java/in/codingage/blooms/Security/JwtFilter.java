package in.codingage.blooms.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        try {

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);

                // Invalid token ko ignore karo
                if (token != null
                        && !token.isBlank()
                        && !"null".equals(token)
                        && token.contains(".")) {

                    String username = jwtUtil.extractUsername(token);

                    if (username != null) {
                        request.setAttribute("username", username);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("JWT Error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}