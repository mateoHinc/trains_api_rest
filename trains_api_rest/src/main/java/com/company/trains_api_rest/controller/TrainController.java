package com.company.trains_api_rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.trains_api_rest.dtos.train_dto.TrainCreateRequest;
import com.company.trains_api_rest.dtos.train_dto.TrainResponse;
import com.company.trains_api_rest.dtos.train_dto.TrainUpdateRequest;
import com.company.trains_api_rest.model.TrainType;
import com.company.trains_api_rest.service.TrainService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("api/trains")
public class TrainController {
    private final TrainService service;

    public TrainController(TrainService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<TrainResponse> create(@Valid @RequestBody TrainCreateRequest req) {        
        TrainResponse created = service.createTrain(req);
        return ResponseEntity.created(URI.create("/api/trains/"+created.getId())).body(created);
    }

    @GetMapping
    public List<TrainResponse> list() {
        return service.listTrain();
    }

    @GetMapping("/{id}")
    public TrainResponse get(@PathVariable Long id) {
        return service.getTrain(id);
    }
    
    @PutMapping("/{id}")
    public TrainResponse update(@PathVariable Long id, @Valid @RequestBody TrainCreateRequest req) {
        return service.updateTrain(id, req);
    }
    
    @PatchMapping("/{id}")
    public TrainResponse patch(@PathVariable Long id, @Valid @RequestBody TrainUpdateRequest req) {
        return service.patchTrain(id, req);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteTrain(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "¡Tren Eliminado exitosamente!");

        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = "type")
    public List<TrainResponse> listType(@RequestParam TrainType type) {
        return service.listType(type);
    }
    
}
