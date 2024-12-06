package com.gomistar.proyecto_gomistar.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gomistar.proyecto_gomistar.configuration.security.filters.JwtAuthenticationFilter;
import com.gomistar.proyecto_gomistar.configuration.security.filters.JwtAuthorizationFilter;
import com.gomistar.proyecto_gomistar.configuration.security.jwt.JwtUtils;
import com.gomistar.proyecto_gomistar.service.UserDetailsServiceImpl;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    private final JwtUtils jwtUtils;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(JwtUtils pJwtUtils, UserDetailsServiceImpl pUserDetailsServiceImpl, JwtAuthorizationFilter pJwtAuthorizationFilter) {
        this.jwtUtils = pJwtUtils;
        this.userDetailsServiceImpl = pUserDetailsServiceImpl;
        this.jwtAuthorizationFilter = pJwtAuthorizationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return httpSecurity.csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers( "http://localhost:8115/document/test").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(sesion -> {
                    sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {

        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                            .userDetailsService(userDetailsServiceImpl)
                            .passwordEncoder(passwordEncoder)
                            .and()
                            .build();
    }
}
