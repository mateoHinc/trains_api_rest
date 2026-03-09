package com.company.trains_api_rest.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.company.trains_api_rest.model.Ticket;
import com.company.trains_api_rest.model.TicketStatus;

/*
    Esta clase contiene especificaciones dinámicas para filtrar tickets.

    "Specifications" permiten construir consultar dinámicas en JPA
    combinando múltiples filtros opcionales sin tener que escribir
    múltiples métodos en el repository.
*/

public class TicketSpecification {

    public static Specification<Ticket> hasRoute(Long routeId) {
        return (root, query, cb) -> routeId == null ? null : cb.equal(root.get("route").get("id"), routeId);
    }

    public static Specification<Ticket> hasTrain(Long trainId) {
        return (root, query, cb) -> trainId == null ? null : cb.equal(root.get("route").get("train").get("id"), trainId);
    }

    public static Specification<Ticket> hasStatus(TicketStatus status) {
        return (root, query, cb) -> status == null ? null: cb.equal(root.get("status"), status);
    }

    public static Specification<Ticket> hasTravelDate(LocalDate travelDate) {
        return (root, query, cb) -> travelDate == null ? null: cb.equal(root.get("travelDate"), travelDate);
    }
    
    public static Specification<Ticket> hasPassengerDocument(String passengerDocument) {
        return (root, query, cb) -> passengerDocument == null ? null: cb.equal(root.get("passengerDocument"), passengerDocument);
    }



}
