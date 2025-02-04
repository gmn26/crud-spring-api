package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.dto.auth.LoginRequestDto;

public interface UserService {
    public String login(LoginRequestDto loginRequestDto);
}
