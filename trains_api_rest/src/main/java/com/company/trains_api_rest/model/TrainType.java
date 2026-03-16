package com.company.trains_api_rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TrainType {
    URBANO,
    LARGA_DISTANCIA,
    MERCANCIA;

    /*
        Convierte automáticamente valores recibidos en JSON
        a mayúsculas para que el enum funcione correctamente.

        Ejemplo:
        "urbano" -> URBANO
        "Urbano" -> URBANO
    */ 
    
    @JsonCreator
    public static TrainType fromString(String value) {
        return TrainType.valueOf(value.trim().toUpperCase());
    }
}
