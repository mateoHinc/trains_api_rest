package com.company.trains_api_rest.dtos.ticket_dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.company.trains_api_rest.model.TicketStatus;

public class TicketResponse {

    private Long id;
    private Long routeId;
    private Long trainId;
    private String trainName;
    private String originStationName;
    private String destinationStationName;
    private String passengerName;
    private String passengerDocumento;
    private BigDecimal price;
    private String seatNumber;
    private LocalDate travelDate;
    private TicketStatus status;
    private Long sellerId;
    private String sellerName;
    private String sellerEmail;
    
    public TicketResponse(Long id, Long routeId, Long trainId, String trainName, String originStationName,
            String destinationStationName, String passengerName, String passengerDocumento, BigDecimal price,
            String seatNumber, LocalDate travelDate, TicketStatus status, Long sellerId, String sellerName, String sellerEmail) {
        this.id = id;
        this.routeId = routeId;
        this.trainId = trainId;
        this.trainName = trainName;
        this.originStationName = originStationName;
        this.destinationStationName = destinationStationName;
        this.passengerName = passengerName;
        this.passengerDocumento = passengerDocumento;
        this.price = price;
        this.seatNumber = seatNumber;
        this.travelDate = travelDate;
        this.status = status;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
    }

    public Long getId() {
        return id;
    }

    public Long getRouteId() {
        return routeId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getOriginStationName() {
        return originStationName;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerDocumento() {
        return passengerDocumento;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }
    
}
