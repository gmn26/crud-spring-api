package com.gmn26.crud.spring.api.bean.auth;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String role;
    private List<SidebarResponse> sidebars;
    private String token;
}
