package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.bean.auth.LoginRequestDto;
import com.gmn26.crud.spring.api.bean.auth.LoginResponse;
import com.gmn26.crud.spring.api.bean.WebResponse;
import com.gmn26.crud.spring.api.bean.auth.RegisterRequestDto;
import com.gmn26.crud.spring.api.bean.auth.RegisterResponse;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.repository.UserRepository;
import com.gmn26.crud.spring.api.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    private final UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<WebResponse<LoginResponse>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(userService.login(loginRequestDto));

        if(loginResponse.getToken() != null) {
            WebResponse<LoginResponse> response = WebResponse.<LoginResponse>builder()
                    .success(true)
                    .message("Login successful")
                    .data(loginResponse)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            WebResponse<LoginResponse> response = WebResponse.<LoginResponse>builder()
                    .success(false)
                    .message("Invalid credential")
                    .data(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<WebResponse<RegisterResponse>> register(@RequestBody RegisterRequestDto registerRequestDto) {
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

            WebResponse<RegisterResponse> response = WebResponse.<RegisterResponse>builder()
                    .success(true)
                    .message("Registered")
                    .data(registerResponse)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
