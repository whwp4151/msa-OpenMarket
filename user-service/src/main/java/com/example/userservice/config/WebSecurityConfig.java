package com.example.userservice.config;

import com.example.userservice.filter.AuthenticationFilter;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, userService, env);

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/user/**").permitAll();
        http.authorizeRequests().antMatchers("/error/**").permitAll();
        http.authorizeRequests().antMatchers("/**")
            .hasIpAddress("0.0.0.0/0")
            .and()
            .authenticationManager(authenticationManager)
            .addFilter(authenticationFilter);

        http.headers().frameOptions().disable();
        return http.build();
    }

}
