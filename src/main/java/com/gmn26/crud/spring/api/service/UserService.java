package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.bean.auth.LoginRequestDto;

public interface UserService {
    public String login(LoginRequestDto loginRequestDto);
}
