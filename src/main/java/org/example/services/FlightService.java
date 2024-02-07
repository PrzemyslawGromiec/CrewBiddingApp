package org.example.services;

import org.example.entities.Flight;
import org.example.entities.Period;
import org.example.repositories.generator.FlightGeneratorFacade;

import java.util.*;
import java.util.stream.Collectors;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();
    private FlightGeneratorFacade flightGenerator;

    public FlightService(FlightGeneratorFacade flightGenerator) {
        this.flightGenerator = flightGenerator;
        flights.addAll(flightGenerator.generateFlights());
    }


    public List<Flight> generateFlightsForPeriod(Period period) {
        return flights.stream()
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .sorted((flight1, flight2) -> flight2.getFlightDuration().compareTo(flight1.getFlightDuration()))
                .collect(Collectors.toList());
    }

    public List<Flight> getFlightsForPeriod(Period period) {
        return flights.stream()
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .collect(Collectors.toList());
    }


    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }


}
