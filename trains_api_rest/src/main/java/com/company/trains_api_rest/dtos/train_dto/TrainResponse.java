package com.company.trains_api_rest.dtos.train_dto;

import com.company.trains_api_rest.model.TrainType;

public class TrainResponse {
    private Long id;
    private String name;
    private Integer capacity;
    private TrainType type;
    
    public TrainResponse(Long id, String name, Integer capacity, TrainType type) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public TrainType getType() {
        return type;
    }

    
}
