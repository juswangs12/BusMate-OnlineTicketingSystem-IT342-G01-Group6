package edu.cit.lgng.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
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

        RequestMatcher apiMatcher = request -> request.getRequestURI().startsWith("/api/");

        AuthenticationEntryPoint apiEntryPoint = (HttpServletRequest req, HttpServletResponse res,
                org.springframework.security.core.AuthenticationException ex) -> {
            if (apiMatcher.matches(req)) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Unauthorized\"}");
            } else {
                res.sendRedirect("/login");
            }
        };

        http
            .cors(cors -> cors.configurationSource(request -> {
                var config = new org.springframework.web.cors.CorsConfiguration();
                config.setAllowedOriginPatterns(java.util.List.of("*"));
                config.setAllowedMethods(java.util.List.of("GET","POST","PUT","DELETE","OPTIONS"));
                config.setAllowedHeaders(java.util.List.of("*"));
                config.setAllowCredentials(true);
                return config;
            }))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 1. React static files (Vite output) — THIS WAS MISSING
                .requestMatchers("/", "/index.html", "/dist/**", "/assets/**", "/static/**", "/images/**", "/favicon.ico", "/css/**", "/js/**").permitAll()

                // 2. Auth endpoints
                .requestMatchers("/api/auth/login", "/api/auth/signup", "/oauth2/**", "/login/**").permitAll()

                // 3. PayMongo webhook
                .requestMatchers("/api/paymongo/webhook").permitAll()

                // 4. Everything else → authenticated (including API calls with JWT)
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(apiEntryPoint))
            .oauth2Login(oauth -> oauth.successHandler(oauth2LoginSuccessHandler))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}