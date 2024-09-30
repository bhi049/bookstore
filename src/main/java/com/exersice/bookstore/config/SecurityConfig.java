package com.exersice.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests((requests) -> requests
        .requestMatchers("/booklist/delete/**").hasRole("ADMIN")
        .anyRequest().authenticated()
      )
      .formLogin((form) ->
        form
        .loginPage("/login")
        .defaultSuccessUrl("/booklist", true)  // Redirect to booklist after login
        .permitAll()
      )
      .logout((logout) -> 
        logout
        .logoutUrl("/logout")  // Specify the logout URL
        .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
        .permitAll()
      );
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
      UserDetails user = User.withDefaultPasswordEncoder()
          .username("user")
          .password("password")
          .roles("USER")
          .build();

      UserDetails admin = User.withDefaultPasswordEncoder()
          .username("admin")
          .password("admin")
          .roles("ADMIN")
          .build();

      return new InMemoryUserDetailsManager(user, admin);
  }
}

