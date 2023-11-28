package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.entities.Period;

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
        flights.addAll(generateCustomRecurringFlights("BA210","WAW", AircraftType.A320,
                LocalTime.of(10,30),Duration.ofHours(10)));
        flights.addAll(generateCustomRecurringFlights("BA150", "FRA",AircraftType.A320,
                LocalTime.of(9,25),Duration.ofHours(8)));
        flights.addAll(generateCustomRecurringFlights("BA243","FCO",AircraftType.A320,
                LocalTime.of(5,25),Duration.ofHours(11)));

    }

    //TODO:metoda moze pominac wybrane dni tygodnia lub daty - przekazac liste dni tygodnia
    //moze byc przeciazona metoda z lista
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

    public void fillPeriodWithFlights(Period period) {

    }


    public List<Flight> getFlights() {
        return flights;
    }

    void setToday(LocalDate today) {
        this.today = today;
    }
}
