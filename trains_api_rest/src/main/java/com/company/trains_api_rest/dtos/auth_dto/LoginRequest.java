package com.company.trains_api_rest.dtos.auth_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Email(message = "El correo electrónico debe ser valido")
    @NotBlank(message = "El correo electrónico es requerido")
    private String email;

    @NotBlank(message = "La contraseña es requerido")
    private String password;

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

    

}
