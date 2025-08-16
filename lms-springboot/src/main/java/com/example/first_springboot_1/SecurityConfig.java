package com.example.first_springboot_1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for testing with Postman
            .authorizeHttpRequests(auth -> auth
                // ✅ Public endpoints
                .requestMatchers("/h2-console/**", "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // ✅ Make admin endpoints public (NO LOGIN REQUIRED)
                .requestMatchers("/api/admin/**").permitAll()

                // Role-based endpoints
                .requestMatchers("/api/students/**").hasRole("STUDENT")
                .requestMatchers("/api/instructors/**").hasRole("INSTRUCTOR")

                // All others require authentication
                .anyRequest().authenticated()
            )
            // Use HTTP Basic Auth for Postman
            .httpBasic(basic -> {})
            // Keep form login for browser testing
            .formLogin(form -> form.permitAll())
            // Logout
            .logout(logout -> logout.permitAll());

        // H2 Console fix (frames allowed from same origin)
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    // In-memory users (for testing)
    @Bean
    public UserDetailsService users(PasswordEncoder encoder) {
        UserDetails instructor = User.withUsername("instructor")
                .password(encoder.encode("inst123"))
                .roles("INSTRUCTOR")
                .build();

        UserDetails student = User.withUsername("student")
                .password(encoder.encode("stud123"))
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(instructor, student);
    }

    // Password encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
