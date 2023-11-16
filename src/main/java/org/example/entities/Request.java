package org.example.entities;

import java.time.LocalDateTime;

public class Request {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int points;
    private RequestType type;
    private String airportCode;
    private String flightNumber;

    public Request(LocalDateTime startTime, LocalDateTime endTime, int points, RequestType type) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.points = points;
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}


