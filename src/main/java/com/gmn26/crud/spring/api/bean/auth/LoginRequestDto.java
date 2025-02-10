package com.gmn26.crud.spring.api.bean.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}
