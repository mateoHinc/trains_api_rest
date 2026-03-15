package com.company.trains_api_rest.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.company.trains_api_rest.model.Ticket;
import com.company.trains_api_rest.model.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket>{

    boolean existsByRouteIdAndTravelDateAndSeatNumberAndStatusNot(
        Long routeId,
        LocalDate traveDate,
        String seatNumber,
        TicketStatus status
    );

    boolean existsByRouteIdAndTravelDateAndSeatNumberAndStatusNotANdIdNot(
        Long routeId,
        LocalDate traveDate,
        String seatNumber,
        TicketStatus status,
        Long id
    );

}
