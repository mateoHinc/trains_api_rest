package com.company.trains_api_rest.dtos.route_dto;

public class RouteResponse {

    private Long id;
    private Long trainId;
    private String trainName;
    private Long originStationId;
    private String originStationName;
    private Long destinationStationId;
    private String destinationStationName;
    private Double distanceKm;
    private Integer estimatedTimeMinutes;
    private Boolean active;
    
    public RouteResponse(Long id, Long trainId, String trainName, Long originStationId, String originStationName,
            Long destinationStationId, String destinationStationName, Double distanceKm, Integer estimatedTimeMinutes,
            Boolean active) {
        this.id = id;
        this.trainId = trainId;
        this.trainName = trainName;
        this.originStationId = originStationId;
        this.originStationName = originStationName;
        this.destinationStationId = destinationStationId;
        this.destinationStationName = destinationStationName;
        this.distanceKm = distanceKm;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getTrainId() {
        return trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public Long getOriginStationId() {
        return originStationId;
    }

    public String getOriginStationName() {
        return originStationName;
    }

    public Long getDestinationStationId() {
        return destinationStationId;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public Integer getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public Boolean getActive() {
        return active;
    }

    

}
