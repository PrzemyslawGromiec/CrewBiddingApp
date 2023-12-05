package org.example.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class Flight {
    private String flightNumber;
    private String airportCode;
    private LocalDateTime reportTime;
    private LocalDateTime clearTime;
    private AircraftType aircraftType;
    private boolean favourite;


    public Flight(String flightNumber, String airportCode, LocalDateTime reportTime, LocalDateTime clearTime, AircraftType aircraftType) {
        this.flightNumber = flightNumber;
        this.airportCode = airportCode;
        this.reportTime = reportTime;
        this.clearTime = clearTime;
        this.aircraftType = aircraftType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }

    public LocalDateTime getClearTime() {
        return clearTime;
    }

    public void setClearTime(LocalDateTime clearTime) {
        this.clearTime = clearTime;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public Duration getFlightDuration() {
        return Duration.between(reportTime,clearTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", airportCode='" + airportCode + '\'' +
                ", reportTime=" + reportTime +
                ", clearTime=" + clearTime +
                ", aircraftType=" + aircraftType +
                ", favourite=" + favourite +
                ", duration=" + getFlightDuration() +
                '}';
    }
}
