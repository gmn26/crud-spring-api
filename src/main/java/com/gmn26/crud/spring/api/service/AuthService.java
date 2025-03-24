package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.JwtProvider.JwtTokenProvider;
import com.gmn26.crud.spring.api.bean.auth.LoginRequestDto;
import com.gmn26.crud.spring.api.bean.auth.LoginResponse;
import com.gmn26.crud.spring.api.bean.auth.SidebarResponse;
import com.gmn26.crud.spring.api.entity.QSidebarMenuEntity;
import com.gmn26.crud.spring.api.entity.SidebarMenuEntity;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AuthService implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LoginResponse login(LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserEntity user = (UserEntity) authentication.getPrincipal();

            String token = jwtTokenProvider.getJwtSecret(authentication);

            LoginResponse response = new LoginResponse();

            response.setToken(token);

            response.setRole(user.getRoles());

            QSidebarMenuEntity qSidebarMenu = QSidebarMenuEntity.sidebarMenuEntity;

            JPAQuery<SidebarMenuEntity> query = new JPAQuery<>(entityManager);

            query.from(qSidebarMenu);

            query.where(qSidebarMenu.grantedAccess.contains(user.getRoles()));

            List<SidebarMenuEntity> sidebars = query.fetch();

            List<SidebarResponse> sidebarResponses = new ArrayList<>();

            for (SidebarMenuEntity sidebar : sidebars) {
                SidebarResponse sidebarResponse = new SidebarResponse();
                sidebarResponse.setTitle(sidebar.getTitle());
                sidebarResponse.setRoute(sidebar.getRoute());

                sidebarResponses.add(sidebarResponse);
            }

            response.setSidebars(sidebarResponses);

            return response;
        } catch (AuthenticationException e) {
            log.error("Authentication failed error: {}", e.getMessage());
            return null;
        }
    }

    public boolean checkTokenExpiration(String token){
        return jwtTokenProvider.validateToken(token);
    }
}
