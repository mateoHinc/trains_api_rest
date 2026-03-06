package com.company.trains_api_rest.dtos.station_dto;

public class StationResponse {
    private Long id;
    private String name;
    private String city;
    private Boolean active;
    
    public StationResponse(Long id, String name, String city, Boolean active) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Boolean getActive() {
        return active;
    }
}
