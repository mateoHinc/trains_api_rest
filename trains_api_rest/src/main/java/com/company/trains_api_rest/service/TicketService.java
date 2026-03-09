package com.company.trains_api_rest.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.company.trains_api_rest.dtos.ticket_dto.TicketCreateRequest;
import com.company.trains_api_rest.dtos.ticket_dto.TicketResponse;
import com.company.trains_api_rest.dtos.ticket_dto.TicketUpdateRequest;
import com.company.trains_api_rest.exception.ResourceNotFoundException;
import com.company.trains_api_rest.model.Route;
import com.company.trains_api_rest.model.Ticket;
import com.company.trains_api_rest.model.TicketStatus;
import com.company.trains_api_rest.repository.RouteRepository;
import com.company.trains_api_rest.repository.TicketRepository;
import com.company.trains_api_rest.repository.TicketSpecification;

@Service
public class TicketService {

    private final TicketRepository ticketRepo;
    private final RouteRepository routeRepo;
    
    public TicketService(TicketRepository ticketRepo, RouteRepository routeRepo) {
        this.ticketRepo = ticketRepo;
        this.routeRepo = routeRepo;
    }

    private Route getRouteOrThrow(Long id) {
        return routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada: "+id));
    }

    private TicketResponse toResponse(Ticket ticket){
        return new TicketResponse(
            ticket.getId(), ticket.getRoute().getId(), ticket.getRoute().getTrain().getId(), ticket.getRoute().getTrain().getName(), ticket.getRoute().getOriginStation().getName(), ticket.getRoute().getDestinationStation().getName(), ticket.getPassengerName(), ticket.getPassengerDocument(), ticket.getPrice(), ticket.getSeatNumber(), ticket.getTravelDate(), ticket.getStatus()
        );
    }

    public TicketResponse createTicket(TicketCreateRequest req){
        Route route = getRouteOrThrow(req.getRouteId());

        Ticket ticket = new Ticket();
        ticket.setRoute(route);
        ticket.setPassengerName(req.getPassengerName());
        ticket.setPassengerDocument(req.getPassengerDocument());
        ticket.setPrice(req.getPrice());
        ticket.setSeatNumber(req.getSeatNumber());
        ticket.setTravelDate(req.getTravelDate());
        ticket.setStatus(req.getStatus());

        return toResponse(ticketRepo.save(ticket));
    }

    public List<TicketResponse> listTickets(Long routeId, Long trainId, TicketStatus status, LocalDate travelDate, String passengerDocument) {
        Specification<Ticket> spec = Specification.where(TicketSpecification.hasRoute(routeId)).and(TicketSpecification.hasTrain(trainId).and(TicketSpecification.hasStatus(status)).and(TicketSpecification.hasTravelDate(travelDate)).and(TicketSpecification.hasPassengerDocument(passengerDocument)));

        return ticketRepo.findAll(spec).stream().map(this::toResponse).toList();
    }

    public TicketResponse getTicket(Long id) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tiquete no encontrado: "+id));

        return toResponse(ticket);
    }

    public TicketResponse updateTicket(Long id, TicketCreateRequest req) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tiquete no encontrado: "+id));

        ticket.setRoute(getRouteOrThrow(req.getRouteId()));
        ticket.setPassengerDocument(req.getPassengerDocument());
        ticket.setPrice(req.getPrice());
        ticket.setSeatNumber(req.getSeatNumber());
        ticket.setTravelDate(req.getTravelDate());
        ticket.setStatus(req.getStatus());

        return toResponse(ticketRepo.save(ticket));
    }

    public TicketResponse patchTicket(Long id, TicketUpdateRequest req) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tiquete no encontrado: "+id));

        if(
            req.getRouteId() == null && req.getPassengerName() == null &&
            req.getPassengerDocument() == null && req.getPrice() == null &&
            req.getSeatNumber() == null && req.getTravelDate() == null &&
            req.getStatus() == null){
            throw new IllegalArgumentException("Se debe proporcionar al menos un campo");
        }

        if(req.getRouteId() != null) ticket.setRoute(getRouteOrThrow(req.getRouteId()));
        if(req.getPassengerName() != null) ticket.setPassengerName(req.getPassengerName());
        if(req.getPassengerDocument() != null) ticket.setPassengerDocument(req.getPassengerDocument());
        if(req.getPrice() != null)  ticket.setPrice(req.getPrice());
        if(req.getSeatNumber() != null) ticket.setSeatNumber(req.getSeatNumber());
        if(req.getTravelDate() != null) ticket.setTravelDate(req.getTravelDate());
        if(req.getStatus() != null) ticket.setStatus(req.getStatus());

        return toResponse(ticketRepo.save(ticket));
    }

    public void deleteTicket(Long id) {
        if(ticketRepo.existsById(id)){
            ticketRepo.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Tiquete no encontrado: "+id);
        }
    }

}
