package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;

import java.time.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();
    private LocalDate today;

    public FlightService() {
        this.today = LocalDate.now();
    }

    public List<Flight> generateCustomRecurringFlights(String flightNumber, String airportCode, AircraftType type, LocalTime takeoffTime, Duration flightLength) {
        List<Flight> flights = new ArrayList<>();
        LocalDateTime takeoffDate = today.plusMonths(1).withDayOfMonth(1).atTime(takeoffTime);
        LocalDateTime landingTime = takeoffDate.plus(flightLength);

        while (takeoffDate.getMonth() == today.plusMonths(1).getMonth()) {

            flights.add(new Flight(flightNumber, airportCode, takeoffDate, landingTime, type));
            takeoffDate = takeoffDate.plusDays(1);
            landingTime = landingTime.plusDays(1);
        }
        return flights;
    }


    public List<Flight> getFlights() {
        return flights;
    }

    void setToday(LocalDate today) {
        this.today = today;
    }
}
