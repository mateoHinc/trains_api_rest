package com.company.trains_api_rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.trains_api_rest.dtos.station_dto.StationCreateRequest;
import com.company.trains_api_rest.dtos.station_dto.StationResponse;
import com.company.trains_api_rest.dtos.station_dto.StationUpdateRequest;
import com.company.trains_api_rest.service.StationService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/stations")
public class StationController {
    private final StationService service;

    public StationController(StationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StationResponse> create(@Valid @RequestBody StationCreateRequest req) {
        StationResponse created = service.createStation(req);
        return ResponseEntity.created(URI.create("/api/stations/" + created.getId())).body(created);
    }

    @GetMapping
    public List<StationResponse> list() {
        return service.ListStations();
    }
    
    @GetMapping("/{id}")
    public StationResponse get(@PathVariable Long id) {
        return service.getStation(id);
    }
    
    @PutMapping("/{id}")
    public StationResponse update(@PathVariable Long id, @Valid @RequestBody StationCreateRequest req) {
        return service.updateStation(id, req);        
    }
    
    @PatchMapping("/{id}")
    public StationResponse patch(@PathVariable Long id, @Valid @RequestBody StationUpdateRequest req) {
        return service.patchStation(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

}
