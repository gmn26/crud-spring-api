package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.bean.auth.LoginRequestDto;
import com.gmn26.crud.spring.api.bean.auth.LoginResponse;

public interface UserService {
    public LoginResponse login(LoginRequestDto loginRequestDto);
}
