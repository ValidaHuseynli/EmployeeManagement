package com.employee.management.config;

import com.employee.management.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/employee_management/auth/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/employee_management/departments/**",
                        "/api/v1/employee_management/employees/**",
                        "/api/v1/employee_management/positions/**")
                .hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/employee_management/departments/**",
                        "/api/v1/employee_management/employees/**",
                        "/api/v1/employee_management/positions/**")
                .hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/employee_management/departments/**",
                        "/api/v1/employee_management/employees/**",
                        "/api/v1/employee_management/positions/**")
                .hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/employee_management/departments/**",
                        "/api/v1/employee_management/employees/**",
                        "/api/v1/employee_management/positions/**")
                .hasAnyAuthority("USER", "ADMIN"))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}

/*
.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)*/
