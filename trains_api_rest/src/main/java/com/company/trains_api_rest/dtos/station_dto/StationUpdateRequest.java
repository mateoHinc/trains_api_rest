package com.company.trains_api_rest.dtos.station_dto;

import jakarta.validation.constraints.Size;

public class StationUpdateRequest {
    @Size(max = 80, message = "El nombre debe ser menor de 80 caracteres.")
    private String name;

    @Size(max =120, message = "El campo ciudad debe ser menor de 120 caracteres.")
    private String city;

    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
