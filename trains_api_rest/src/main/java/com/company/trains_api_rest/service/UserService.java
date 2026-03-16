package com.company.trains_api_rest.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.trains_api_rest.model.Role;
import com.company.trains_api_rest.model.User;
import com.company.trains_api_rest.dtos.user_dto.UserCreateRequest;
import com.company.trains_api_rest.dtos.user_dto.UserResponse;
import com.company.trains_api_rest.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(UserCreateRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("¡El correo electrónico ya existe!");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole() != null ? req.getRole() : Role.USUARIO);
        user.setActive(true);

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getActive());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("¡Credenciales invalidas!"));
    }

}
