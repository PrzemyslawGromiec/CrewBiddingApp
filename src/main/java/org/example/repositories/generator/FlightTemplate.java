package org.example.repositories.generator;

import org.example.entities.AircraftType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class FlightTemplate {
    private String flightNumber;
    private String airportCode;
    private LocalTime reportTime;
    private LocalTime clearTime = LocalTime.MAX;
    private int durationDays = 0;
    private List<DayOfWeek> days = new ArrayList<>();  //empty list = all days of the week
    private AircraftType aircraftType = AircraftType.A320;


    public FlightTemplate(String flightNumber, String airportCode, LocalTime reportTime, LocalTime clearTime, int durationDays, List<DayOfWeek> days, AircraftType aircraftType) {
        this.flightNumber = flightNumber;
        this.airportCode = airportCode;
        this.reportTime = reportTime;
        this.clearTime = clearTime;
        this.durationDays = durationDays;
        this.days = days;
        this.aircraftType = aircraftType;
    }

    public FlightTemplate(String flightNumber, String airportCode, LocalTime reportTime) {
        this.flightNumber = flightNumber;
        this.airportCode = airportCode;
        this.reportTime = reportTime;
    }
    public List<DayOfWeek> getDays() {

        if (days.isEmpty()) {
            return List.of(DayOfWeek.values());
        }
        return days;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public LocalTime getReportTime() {
        return reportTime;
    }

    public LocalTime getClearTime() {
        return clearTime;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }
}
