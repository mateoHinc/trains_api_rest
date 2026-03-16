package com.company.trains_api_rest.dtos.user_dto;

import com.company.trains_api_rest.model.Role;

public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Boolean active;
    
    public UserResponse(Long id, String name, String email, Role role, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
    }

}
