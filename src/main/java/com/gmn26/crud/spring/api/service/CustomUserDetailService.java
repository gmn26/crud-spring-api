package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database

        // Map roles string to GrantedAuthority (assuming comma-separated roles like "ROLE_USER,ROLE_ADMIN")
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        for (String role : userEntity.getRoles().split(",")) {
//            authorities.add(new SimpleGrantedAuthority(role.trim()));  // Convert roles to SimpleGrantedAuthority
//        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));  // Return UserEntity which implements UserDetails
    }
}
