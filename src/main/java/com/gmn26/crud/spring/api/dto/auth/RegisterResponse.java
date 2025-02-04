package com.gmn26.crud.spring.api.dto.auth;

import lombok.Data;

@Data
public class RegisterResponse {
    private String name;
    private String username;
    private String role;
}
