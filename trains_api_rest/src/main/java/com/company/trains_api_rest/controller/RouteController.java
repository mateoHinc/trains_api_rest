package com.company.trains_api_rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.trains_api_rest.dtos.route_dto.RouteCreateRequest;
import com.company.trains_api_rest.dtos.route_dto.RouteResponse;
import com.company.trains_api_rest.dtos.route_dto.RouteUpdateRequest;
import com.company.trains_api_rest.service.RouteService;

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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService service;

    public RouteController(RouteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RouteResponse> create(@Valid @RequestBody RouteCreateRequest req) {
        RouteResponse created = service.createRoute(req);
        return ResponseEntity.created(URI.create("/api/routes/"+created.getId())).body(created);
    }
    
    @GetMapping
    public List<RouteResponse> list() {
        return service.listRoutes();
    }
    
    @GetMapping("/{id}")
    public RouteResponse get(@PathVariable Long id) {
        return service.getRoute(id);
    }
    
    @PutMapping("/{id}")
    public RouteResponse update(@PathVariable Long id, @Valid @RequestBody RouteCreateRequest req) {
        return service.updateRoute(id, req);
    }
    
    @PatchMapping("/{id}")
    public RouteResponse patch(@PathVariable Long id, @Valid @RequestBody RouteUpdateRequest req) {
        return service.patchRoute(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id){
        service.deleteRoute(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "¡Ruta Eliminada exitosamente!");

        return ResponseEntity.ok(response);
    }

}
