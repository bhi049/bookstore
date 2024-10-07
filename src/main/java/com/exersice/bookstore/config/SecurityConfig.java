package com.exersice.bookstore.config;

import com.exersice.bookstore.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/h2-console/**").permitAll()  // Allow access to H2 console
                .requestMatchers("/booklist/delete/**").hasRole("ADMIN")  // ADMIN role for delete
                .anyRequest().authenticated()  // All other requests require authentication
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/booklist", true)  // Redirect to booklist after successful login
                .permitAll()  // Allow everyone to access the login page
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                .permitAll()
            )
            .headers().frameOptions().sameOrigin();  // Allow frames for H2 console access

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailsService;  // Use MyUserDetailsService for user authentication
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt for password encoding
    }
}

