package com.security.spring_security_course.service;

import com.security.spring_security_course.dto.AuthRequest;
import com.security.spring_security_course.dto.AuthResponse;
import com.security.spring_security_course.entity.User;
import com.security.spring_security_course.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
        authenticationManager.authenticate(token);
        User user = userRepository.findByUserName(request.getUserName()).get();
        String jwt = jwtService.generateToken(user,generateExtraClaims(user));
        return new AuthResponse(jwt);
    }

    private Map<String,Object> generateExtraClaims(User user) {
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("name",user.getName());
        claims.put("role",user.getRole());
        return claims;
    }
}
