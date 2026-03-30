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
import com.company.trains_api_rest.model.User;
import com.company.trains_api_rest.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class TicketService {

    private final TicketRepository ticketRepo;
    private final RouteRepository routeRepo;
    private final UserService userService;
    
    public TicketService(TicketRepository ticketRepo, RouteRepository routeRepo, UserService userService) {
        this.ticketRepo = ticketRepo;
        this.routeRepo = routeRepo;
        this.userService = userService;
    }

    private Route getRouteOrThrow(Long id) {
        return routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada: "+id));
    }

    private String normalizeSeatNumber(String seatNumber) {
        return seatNumber == null ? null : seatNumber.trim().toUpperCase();
    }

    private TicketResponse toResponse(Ticket ticket){
        return new TicketResponse(
            ticket.getId(), ticket.getRoute().getId(), ticket.getRoute().getTrain().getId(), ticket.getRoute().getTrain().getName(), ticket.getRoute().getOriginStation().getName(), ticket.getRoute().getDestinationStation().getName(), ticket.getPassengerName(), ticket.getPassengerDocument(), ticket.getPrice(), ticket.getSeatNumber(), ticket.getTravelDate(), ticket.getStatus(), ticket.getSeller().getId(), ticket.getSeller().getUsername(), ticket.getSeller().getEmail()
        );
    }

    public TicketResponse createTicket(TicketCreateRequest req){
        // Obtiene la ruta o lanza error si no existe
        Route route = getRouteOrThrow(req.getRouteId());

        // Normalizar minúscula a mayúscula el asiento antes de validar
        String normalizedSeat = normalizeSeatNumber(req.getSeatNumber());

        validateSeatAvailability(req.getRouteId(), req.getTravelDate(), normalizedSeat);

        User seller = getAuthenticatedUser();

        Ticket ticket = new Ticket();
        ticket.setRoute(route);
        ticket.setSeller(seller);
        ticket.setPassengerName(req.getPassengerName());
        ticket.setPassengerDocument(req.getPassengerDocument());
        ticket.setPrice(req.getPrice());
        ticket.setSeatNumber(normalizedSeat);
        ticket.setTravelDate(req.getTravelDate());
        ticket.setStatus(req.getStatus());

        return toResponse(ticketRepo.save(ticket));
    }

    public List<TicketResponse> listTickets(Long routeId, Long trainId, TicketStatus status, LocalDate travelDate, String passengerDocument, Long sellerId) {
        Specification<Ticket> spec = Specification.where(TicketSpecification.hasRoute(routeId)).and(TicketSpecification.hasTrain(trainId).and(TicketSpecification.hasStatus(status)).and(TicketSpecification.hasTravelDate(travelDate)).and(TicketSpecification.hasPassengerDocument(passengerDocument)).and(TicketSpecification.hasSeller(sellerId)));

        return ticketRepo.findAll(spec).stream().map(this::toResponse).toList();
    }

    public TicketResponse getTicket(Long id) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tiquete no encontrado: "+id));

        return toResponse(ticket);
    }

    public TicketResponse updateTicket(Long id, TicketCreateRequest req) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tiquete no encontrado: "+id));

        validateSeatAvailabilityForUpdate(id, req.getRouteId(), req.getTravelDate(), req.getSeatNumber());

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

        Long finalRoutedId = req.getRouteId() != null ? req.getRouteId() : ticket.getRoute().getId();

        LocalDate finalTravelDate = req.getTravelDate() != null ? req.getTravelDate() : ticket.getTravelDate();

        String finalSeatNumber = req.getSeatNumber() != null ? req.getSeatNumber() : ticket.getSeatNumber();

        validateSeatAvailabilityForUpdate(id, finalRoutedId, finalTravelDate, finalSeatNumber);

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

    /*
        Regla de Negocio
        No puede existir otro ticket con el mismo:
            - routeId, Id de la ruta
            - travelDate, fecha del viaje
            - seatNumber, número de asiento
        
        Se ignoran los tickets CANCELADO porque esos asientos pueden reutilizarse
    */

    private void validateSeatAvailability(Long routeId, LocalDate travelDate, String seatNumber) {
        // Consulta en base de datos si ua existe un ticket con ese asiento
        // para  la misma ruta y fecha que no esté cancelado
        boolean exists = ticketRepo.existsByRouteIdAndTravelDateAndSeatNumberAndStatusNot(routeId, travelDate, seatNumber, TicketStatus.CANCELADO);
        
        // Si ya existe, se lanza una excepción indicando que el asiento está ocupado 
        if(exists){
            throw new IllegalArgumentException(
                "El asiento: "+ seatNumber+ " ya está asignado por esta ruta "+ routeId +" el "+travelDate
            );
        }
    }

    private void validateSeatAvailabilityForUpdate(Long ticketId, Long routeId, LocalDate travelDate, String seatNumber) {
        boolean exists = ticketRepo.existsByRouteIdAndTravelDateAndSeatNumberAndStatusNotAndIdNot(routeId, travelDate, seatNumber, TicketStatus.CANCELADO,ticketId);

        if(exists){
            throw new IllegalArgumentException(
                "El asiento: "+ seatNumber+ " ya está asignado por esta ruta "+ routeId +" el "+travelDate
            );
        }
    }

    /*
        Obtiene el usuario autenticado actualmente desde el contexto de Spring Security.

        El email viene del JWT validado en el filtro.

        return usuario autenticado
    */
   private User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication == null || authentication.getName() == null) {
        throw new IllegalArgumentException("Usuario autenticado no encontrado");
    }

    String email = authentication.getName();
    return userService.findByEmail(email);
   }

}
