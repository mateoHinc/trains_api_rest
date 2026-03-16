package com.company.trains_api_rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {

    ADMIN,
    OPERADOR,
    VENDEDOR,
    USUARIO;

    @JsonCreator
    public static Role fromString(String value){
        return Role.valueOf(value.trim().toUpperCase());
    }

}
