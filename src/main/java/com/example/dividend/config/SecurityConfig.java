package com.example.dividend.config;

import com.example.dividend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter authenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .httpBasic(h -> h.disable())
      .csrf(c -> c.disable())
      .sessionManagement(s ->
        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

      .authorizeHttpRequests(request ->
        request
          .requestMatchers(PathRequest.toH2Console()).permitAll()
          .requestMatchers("/error/**").permitAll()
          .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
          .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
          .anyRequest().authenticated()
      )
      .addFilterBefore(
        this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
  public WebSecurityCustomizer configureH2ConsoleEnable() {
    return web -> web.ignoring()
      .requestMatchers(PathRequest.toH2Console());
  }
}
