package com.gmn26.crud.spring.api.bean.auth;

import lombok.Data;

@Data
public class RegisterResponse {
    private String name;
    private String username;
    private String role;
}
