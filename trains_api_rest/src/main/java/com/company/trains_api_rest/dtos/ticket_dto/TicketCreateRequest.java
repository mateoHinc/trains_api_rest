package com.company.trains_api_rest.dtos.ticket_dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.company.trains_api_rest.model.TicketStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TicketCreateRequest {

    @NotNull(message = "El id de ruta es requerida")
    private Long routeId;

    @NotBlank(message = "El nombre del pasajero es requerido")
    @Size(max = 120, message = "El nombre del pasajero debe ser menor de 120 caracteres.")
    private String passengerName;

    @NotBlank(message = "El número de documento del pasajero es requerido")
    @Size(max = 30, message = "El número de documento del pasajero debe ser menor de 30 caracteres.")
    private String passengerDocument;

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0.01")
    private BigDecimal price;

    @NotBlank(message = "El número de asiento es requerido")
    @Size(max = 20, message = "El número de asiento debe ser menor a 20 caracteres")
    private String seatNumber;

    @NotNull(message = "La cita de viaje es requerido")
    private LocalDate travelDate;

    @NotNull(message = "El estado del ticket es requerido")
    private TicketStatus status;

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerDocument() {
        return passengerDocument;
    }

    public void setPassengerDocument(String passengerDocument) {
        this.passengerDocument = passengerDocument;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    
}
