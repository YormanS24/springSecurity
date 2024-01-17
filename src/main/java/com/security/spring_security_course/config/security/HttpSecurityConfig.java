package com.security.spring_security_course.config.security;

import com.security.spring_security_course.filter.JwtAuthenticationFilter;
import com.security.spring_security_course.util.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class HttpSecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;

    public HttpSecurityConfig(JwtAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
         httpSecurity
                 .csrf(AbstractHttpConfigurer::disable)
                 .sessionManagement( httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authenticationProvider(authenticationProvider)
                 .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                 .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                     authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                     authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,"/auth/public-access").permitAll();
                     authorizationManagerRequestMatcherRegistry.requestMatchers("/error").permitAll();

                     authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,"/products").hasAuthority(Permission.READ_ALL_PRODUCTS.name());
                     authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,"products").hasAuthority(Permission.SAVE_ONE_PRODUCT.name());
                     authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                 });
        return httpSecurity.build();
    }
}
