package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.JwtProvider.JwtTokenProvider;
import com.gmn26.crud.spring.api.bean.WebResponse;
import com.gmn26.crud.spring.api.bean.auth.LoginRequestDto;
import com.gmn26.crud.spring.api.bean.auth.RegisterRequestDto;
import com.gmn26.crud.spring.api.bean.auth.RegisterResponse;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final ValidationService validationService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequestDto loginRequestDto) {
        validationService.validate(loginRequestDto);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtTokenProvider.getJwtSecret(authentication);

        } catch (AuthenticationException e) {
            return null;
        }
    }

    public RegisterResponse register(RegisterRequestDto registerRequestDto) {
        validationService.validate(registerRequestDto);
        try {
            String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());
            UserEntity userEntity = new UserEntity();
            userEntity.setName(registerRequestDto.getName());
            userEntity.setUsername(registerRequestDto.getUsername());
            userEntity.setPassword(encodedPassword);
            userEntity.setRoles(registerRequestDto.getRole());
            userRepository.save(userEntity);

            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setName(registerRequestDto.getName());
            registerResponse.setUsername(registerRequestDto.getUsername());
            registerResponse.setRole(registerRequestDto.getRole());

            return registerResponse;
        } catch (Exception e) {
            return null;
        }
    }
}
