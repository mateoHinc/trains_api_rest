package com.company.trains_api_rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.trains_api_rest.dtos.station_dto.StationCreateRequest;
import com.company.trains_api_rest.dtos.station_dto.StationResponse;
import com.company.trains_api_rest.dtos.station_dto.StationUpdateRequest;
import com.company.trains_api_rest.exception.ResourceNotFoundException;
import com.company.trains_api_rest.model.Station;
import com.company.trains_api_rest.repository.StationRepository;

@Service
public class StationService {

    private final StationRepository repo;

    public StationService(StationRepository repo) {
        this.repo = repo;
    }
    
    private StationResponse toResponse(Station s){
        return new StationResponse(s.getId(), s.getName(), s.getName(), s.getActive());
    }

    public StationResponse createStation(StationCreateRequest req){
        Station s = new Station();
        s.setName(req.getName());
        s.setCity(req.getCity());
        s.setActive(req.getActive() != null ? req.getActive() : true);
        return toResponse(repo.save(s));
    }

    public List<StationResponse> ListStations() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public StationResponse getStation(Long id){
        Station s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estación no encontrado: "+id));
        return toResponse(s);
    }

    public StationResponse updateStation(Long id, StationCreateRequest req){
        Station s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estación no encontrado: "+id));

        s.setName(req.getName());
        s.setCity(req.getCity());
        s.setActive(req.getActive() != null ? req.getActive() : s.getActive());

        return toResponse(repo.save(s));
    }

    public StationResponse patchStation(Long id, StationUpdateRequest req){
        Station s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estación no encontrado: "+id));

        if(req.getName() != null) {
            s.setName(req.getName());
        }
        if(req.getCity() != null) {
            s.setCity(req.getCity());
        }
        if(req.getActive() != null) {
            s.setActive(req.getActive());
        }

        if(req.getName() == null && req.getCity() == null && req.getActive() == null){
            throw new IllegalArgumentException("Debe actualizar al menos un campo");
        }

        return toResponse(repo.save(s));
    }

    public void delete(Long id){
        if(!repo.existsById(id)){
            throw new ResourceNotFoundException("Estación no encontrado: "+id);
        }
        repo.deleteById(id);
    }

}
