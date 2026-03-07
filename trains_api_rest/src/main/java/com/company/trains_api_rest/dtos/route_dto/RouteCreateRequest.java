package com.company.trains_api_rest.dtos.route_dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RouteCreateRequest {

    @NotNull(message = "El id del tren es requerido")
    private Long trainId;

    @NotNull(message = "El id de la estación de origen es requerido")
    private Long originStationId;

    @NotNull(message = "El id de la estación destino es requerido")
    private Long destinationStationId;

    @NotNull(message = "La distancia en Km es requerido")
    @Min(value = 100, message = "Los kilometros debe ser mayor a 100")
    private Double distanceKm;

    @NotNull(message = "El tiempo estimado es requerido")
    @Min(value = 60, message = "El tiempo estimado en minutos debe ser mayor a 60")
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
