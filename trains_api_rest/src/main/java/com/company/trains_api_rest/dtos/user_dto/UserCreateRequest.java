package com.company.trains_api_rest.dtos.user_dto;

import com.company.trains_api_rest.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCreateRequest {

    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;

    @Email(message = "El correo electronico debe ser válido")
    @NotBlank(message = "El correo electrónico es requerido")
    private String email;

    @NotBlank(message = "La contraseña es requerido")
    private String password;

    private Role role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
