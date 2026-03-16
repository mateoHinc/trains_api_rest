package com.company.trains_api_rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TicketStatus {
    RESERVADO,
    PAGADO,
    CANCELADO,
    USADO;

    /*
        Convierte automáticamente valores recibidos en JSON
        a mayúsculas para que el enum funcione correctamente.

        Ejemplo:
        "pagado" -> PAGADO
        "Pagado" -> PAGADO
    */ 
    
    @JsonCreator
    public static TicketStatus fromString(String value) {
        return TicketStatus.valueOf(value.trim().toUpperCase());
    }
}
