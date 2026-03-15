package com.company.trains_api_rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.trains_api_rest.dtos.ticket_dto.TicketCreateRequest;
import com.company.trains_api_rest.dtos.ticket_dto.TicketResponse;
import com.company.trains_api_rest.dtos.ticket_dto.TicketUpdateRequest;
import com.company.trains_api_rest.model.TicketStatus;
import com.company.trains_api_rest.service.TicketService;

import jakarta.validation.Valid;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody TicketCreateRequest req) {
        TicketResponse created = service.createTicket(req);
        return ResponseEntity.created(URI.create("/api/tickets/"+ created.getId())).body(created);
    }
    
    @GetMapping
    public List<TicketResponse> list(
        @RequestParam(required = false) Long routedId,
        @RequestParam(required = false) Long trainId,
        @RequestParam(required = false) TicketStatus status,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate,
        @RequestParam(required = false) String passengerDocument
    ) {
        return service.listTickets(routedId, trainId, status, travelDate, passengerDocument);
    }

    @GetMapping("/{id}")
    public TicketResponse get(@PathVariable Long id) {
        return service.getTicket(id);
    }

    @PutMapping("/{id}")
    public TicketResponse update(@PathVariable Long id, @Valid @RequestBody TicketCreateRequest req) {        
        return service.updateTicket(id, req);
    }    

    @PatchMapping("/{id}")
    public TicketResponse patch(@PathVariable Long id, @Valid @RequestBody TicketUpdateRequest req) {        
        return service.patchTicket(id, req);
    }    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
