package com.company.trains_api_rest.dtos.route_dto;

import jakarta.validation.constraints.Min;

public class RouteUpdateRequest {

    private Long trainId;
    private Long originStationId;
    private Long destinationStationId;

    @Min(value = 1, message = "Los kilometros debe ser mayor a 1")
    private Double distanceKm;

    @Min(value = 1, message = "El tiempo estimado en minutos debe ser mayor a 1")
    private Double estimatedTimeMinutes;

    private Boolean active;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getOriginStationId() {
        return originStationId;
    }

    public void setOriginStationId(Long originStationId) {
        this.originStationId = originStationId;
    }

    public Long getDestinationStationId() {
        return destinationStationId;
    }

    public void setDestinationStationId(Long destinationStationId) {
        this.destinationStationId = destinationStationId;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Double getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public void setEstimatedTimeMinutes(Double estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
