package com.example.kioskhelper.config;


import com.example.kioskhelper.auth.filter.CustomAuthorizationFilter;
import com.example.kioskhelper.repository.UserRepository;
import com.example.kioskhelper.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final EncryptionService encryptionService;

    private final UserRepository userRepository;

    private static final String [] PERMIT_ALL_LIST = {
            "/api/auth/login",
            "/api/swagger.json",
            "/swagger-ui/index.html",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/chat/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterAfter(new CustomAuthorizationFilter(encryptionService,userRepository), UsernamePasswordAuthenticationFilter.class);

        http.cors().configurationSource(corsConfigurationSource());


        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
