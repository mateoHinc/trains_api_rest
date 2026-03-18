package com.company.trains_api_rest.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.trains_api_rest.dtos.auth_dto.AuthResponse;
import com.company.trains_api_rest.dtos.auth_dto.LoginRequest;
import com.company.trains_api_rest.dtos.user_dto.UserCreateRequest;
import com.company.trains_api_rest.dtos.user_dto.UserResponse;
import com.company.trains_api_rest.model.User;
import com.company.trains_api_rest.security.JwtService;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(UserCreateRequest req) {
        return userService.registerUser(req);
    }

    public AuthResponse login(LoginRequest req){
        User user = userService.findByEmail(req.getEmail());

        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Credenciales no válidas.");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }

}
