package com.security.spring_security_course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthRequest {
    @JsonProperty(value = "user_name")
    private String userName;
    @JsonProperty(value = "password")
    private String password;

    public AuthRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
