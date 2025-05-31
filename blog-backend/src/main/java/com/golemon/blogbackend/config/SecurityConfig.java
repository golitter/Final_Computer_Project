package com.golemon.blogbackend.config;

import com.golemon.blogbackend.filter.JwtAuthenticationTokenFilter;
import com.golemon.blogbackend.filter.IpRateLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

/**
 * Spring Security configuration class SecurityConfig,
 * used to configure the application's security policies. Specifically, it configures authentication methods,
 * authorization strategies, JWT authentication filters, and exception handling.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private IpRateLimitFilter ipRateLimitFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/login").anonymous()
                .requestMatchers("/user/logout").authenticated()
                .requestMatchers(HttpMethod.PUT, "/article").authenticated()
                .requestMatchers(HttpMethod.POST, "/article").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/article").authenticated()
                .requestMatchers(HttpMethod.POST, "/manage/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/manage/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/manage/**").authenticated()
                .anyRequest().permitAll();

        http.addFilterBefore(ipRateLimitFilter, SecurityContextHolderAwareRequestFilter.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http.logout().disable();
        http.cors();

        return http.build();
    }
}