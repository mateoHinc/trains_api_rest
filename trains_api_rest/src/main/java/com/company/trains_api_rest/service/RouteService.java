package com.company.trains_api_rest.service;

import java.util.List;

import com.company.trains_api_rest.dtos.route_dto.RouteCreateRequest;
import com.company.trains_api_rest.dtos.route_dto.RouteResponse;
import com.company.trains_api_rest.dtos.route_dto.RouteUpdateRequest;
import com.company.trains_api_rest.exception.ResourceNotFoundException;
import com.company.trains_api_rest.model.Route;
import com.company.trains_api_rest.model.Station;
import com.company.trains_api_rest.model.Train;
import com.company.trains_api_rest.repository.RouteRepository;
import com.company.trains_api_rest.repository.StationRepository;
import com.company.trains_api_rest.repository.TrainRepository;

import jakarta.annotation.Resource;

public class RouteService {

    private final RouteRepository routeRepo;
    private final TrainRepository trainRepo;
    private final StationRepository stationRepo;
    
    public RouteService(RouteRepository routeRepo, TrainRepository trainRepo, StationRepository stationRepo) {
        this.routeRepo = routeRepo;
        this.trainRepo = trainRepo;
        this.stationRepo = stationRepo;
    }

    private Train getTrainOrThrow(Long id) {
        return trainRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tren no encontrado: "+id));
    }

    private Station getStationOrThrow(Long id) {
        return stationRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estación no encontrado:"+id));
    }

    private void validateRouteStations(Long originId, Long destinationId){
        if(originId.equals(destinationId)){
            throw new IllegalArgumentException("La estación de origen y destino no puede ser el mismo");
        }
    }

    private RouteResponse toResponse(Route route){
        return new RouteResponse(
            route.getId(),
            route.getTrain().getId(),
            route.getTrain().getName(),
            route.getOriginStation().getId(),
            route.getOriginStation().getName(),
            route.getDestinationStation().getId(),
            route.getDestinationStation().getName(),
            route.getDistanceKm(),route.getEstimatedTimeMinutes(),
            route.getActive());
    }

    public RouteResponse createRoute(RouteCreateRequest req){
        validateRouteStations(req.getOriginStationId(), req.getDestinationStationId());

        Train train = getTrainOrThrow(req.getTrainId());
        Station origin = getStationOrThrow(req.getOriginStationId());
        Station destination = getStationOrThrow(req.getDestinationStationId());

        Route route = new Route();
        route.setTrain(train);
        route.setOriginStation(origin);
        route.setDestinationStation(destination);
        route.setDistanceKm(req.getDistanceKm());
        route.setEstimatedTimeMinutes(req.getEstimatedTimeMinutes());
        route.setActive(req.getActive() != null ? req.getActive() : true);

        return toResponse(routeRepo.save(route));
    }

    public RouteResponse patchRoute(Long id, RouteUpdateRequest req) {
        Route route = routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada: "+id));

        if (
            req.getTrainId() == null &&
            req.getOriginStationId() == null &&
            req.getDestinationStationId() == null &&
            req.getDistanceKm() == null &&
            req.getEstimatedTimeMinutes() == null &&
            req.getActive() == null
        ) {
            throw new IllegalArgumentException("Se debe proporcionar al menos un campo");
        }

        Long originId = req.getOriginStationId() != null ? req.getOriginStationId() : route.getOriginStation().getId();

        Long destinationId = req.getDestinationStationId() != null ? req.getDestinationStationId() : route.getDestinationStation().getId();

        validateRouteStations(originId, destinationId);

        if(req.getTrainId() != null){
            route.setTrain(getTrainOrThrow(req.getTrainId()));
        }

        if(req.getOriginStationId() != null){
            route.setOriginStation(getStationOrThrow(req.getOriginStationId()));
        }

        if(req.getDestinationStationId() != null){
            route.setDestinationStation(getStationOrThrow(req.getDestinationStationId()));
        }

        if(req.getDistanceKm() != null){
            route.setDistanceKm(req.getDistanceKm());
        }

        if(req.getActive() != null){
            route.setActive(req.getActive());
        }

        return toResponse(routeRepo.save(route));
    }

    public void deleteStation(Long id){
        if(routeRepo.existsById(id)){
            routeRepo.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Ruta no encontrada: "+id);
        }
    }

}
