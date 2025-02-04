package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.JwtProvider.JwtTokenProvider;
import com.gmn26.crud.spring.api.dto.auth.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        try {
            log.info("Login request: {}", loginRequestDto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()
                    )
            );

            // Log the authentication object
            log.info("Authentication result: {}", authentication);

            // Set authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            return jwtTokenProvider.getJwtSecret(authentication);

        } catch (AuthenticationException e) {
            log.error("Authentication failed error: {}", e.getMessage());
            return null;
        }
    }
}
