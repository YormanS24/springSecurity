package com.security.spring_security_course.filter;

import com.security.spring_security_course.entity.User;
import com.security.spring_security_course.repository.UserRepository;
import com.security.spring_security_course.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public JwtAuthenticationFilter(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. sacamos el jwt de la peticion
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        //2. obtenemos el jwt
        String jwt = authHeader.split(" ")[1];

        //3. obtener el sub/username del jwt
        String userName = jwtService.extractorUserName(jwt);

        //4. setear un objeto Authentication dentro del springSecurityContext
        User user = userRepository.findByUserName(userName).get();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //5. ejecutar el resto de filtros
        filterChain.doFilter(request,response);
    }
}
