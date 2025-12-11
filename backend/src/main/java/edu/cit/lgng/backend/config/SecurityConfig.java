package edu.cit.lgng.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(request -> {
                var config = new org.springframework.web.cors.CorsConfiguration();
                config.setAllowedOriginPatterns(java.util.List.of("*"));
                config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(java.util.List.of("*"));
                config.setAllowCredentials(true);
                return config;
            }))
            .csrf(csrf -> csrf.disable())

            // CRITICAL CHANGE: Allow sessions when required (for OAuth2 flow only)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )

            .authorizeHttpRequests(auth -> auth
                // Public static assets (React build)
                .requestMatchers("/", "/index.html", "/dist/**", "/assets/**", "/static/**", 
                                 "/images/**", "/favicon.ico", "/css/**", "/js/**").permitAll()

                // OAuth2 endpoints must be public
                .requestMatchers("/oauth2/**", "/login/**").permitAll()

                // Your custom auth endpoints
                .requestMatchers("/api/auth/login", "/api/auth/signup", "/api/paymongo/webhook").permitAll()

                // Protected admin routes
                .requestMatchers("/admin/**").authenticated()

                // Everything else authenticated (protected by JWT)
                .anyRequest().authenticated()
            )

            // Custom 401 handling: JSON for API, redirect for web
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, authEx) -> {
                    if (req.getRequestURI().startsWith("/api/")) {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.setContentType("application/json");
                        res.getWriter().write("{\"error\":\"Unauthorized - Invalid or missing token\"}");
                    } else {
                        res.sendRedirect("/"); // React SPA handles showing login UI
                    }
                })
            )

            // Google OAuth2 Login - now works
            .oauth2Login(oauth -> oauth
                .loginPage("/")                     // Redirect to React root (your login page)
                .successHandler(oauth2LoginSuccessHandler)  // MUST issue JWT here
            )

            // JWT filter protects API calls after login
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}