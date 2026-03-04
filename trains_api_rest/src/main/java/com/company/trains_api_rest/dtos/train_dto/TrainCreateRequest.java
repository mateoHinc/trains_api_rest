package com.company.trains_api_rest.dtos.train_dto;

import com.company.trains_api_rest.model.TrainType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TrainCreateRequest {

    @NotBlank(message = "Nombre es requerido")
    @Size(max = 80, message = "Nombre debe ser menos de 80 caracteres")
    private String name;

    @NotNull(message = "Capacidad er requerida")
    @Min(value = 20, message = "Capacidad debe ser mayor o igual a 20")
    private Integer capacity;

    @NotNull(message = "Tipo de tren es requerido")
    private TrainType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public TrainType getType() {
        return type;
    }

    public void setType(TrainType type) {
        this.type = type;
    }

    

}
