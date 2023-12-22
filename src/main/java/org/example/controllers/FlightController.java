package org.example.controllers;

import org.example.entities.FlightRequest;
import org.example.entities.Period;
import org.example.services.FlightRequestFactory;
import org.example.services.FlightService;
import org.example.entities.Flight;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightController {
    private FlightPeriodController periodController;
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
        periodController = new FlightPeriodController(flightService);
    }

    public List<FlightRequest> chooseFlightsForPeriods(List<Period> generatedPeriods) {
        FlightRequestFactory factory = new FlightRequestFactory();

        for (Period generatedPeriod : generatedPeriods) {
            processSinglePeriod(generatedPeriod, factory);
        }
        return factory.getRequests();
    }

    private void processSinglePeriod(Period initialPeriod, FlightRequestFactory factory) {
        Period currentPeriod = initialPeriod;
        System.out.println("Processing period: " + currentPeriod);

        List<Flight> flightsForCurrentPeriod = flightService.getFlightsForPeriod(currentPeriod); //todo refactor
        if (flightsForCurrentPeriod.isEmpty()) {
            System.out.println("No flights for this period");
            return;
        }

        Flight selectedFlight = periodController.chooseFlight(currentPeriod, factory);

        if (selectedFlight == null) {
            System.out.println("No flight selected for this period.");
            return;
        }

        List<Period> newCreatedPeriods = currentPeriod.splitPeriodAroundSelectedFlight(selectedFlight);

        processSinglePeriod(newCreatedPeriods.get(0), factory);
        processSinglePeriod(newCreatedPeriods.get(1),factory);
    }

    private boolean hasAvailableFlightsInThisPeriod(Period currentPeriod, List<Flight> flights) {
        LocalDateTime startOfPeriod = currentPeriod.getStartTime();
        LocalDateTime endOfPeriod = currentPeriod.getEndTime();

        return flights.stream()
                .anyMatch(flight -> !flight.getReportTime().isBefore(startOfPeriod) &&
                        !flight.getClearTime().isAfter(endOfPeriod) &&
                        flight.getReportTime().isBefore(flight.getClearTime()));
    }

    private List<Flight> getPossibleNextFlights(Flight selectedFlight, List<Flight> availableFlights) {
        return availableFlights.stream()
                .filter(nextFlight -> isSufficientBreakBetweenFlights(selectedFlight, nextFlight))
                .collect(Collectors.toList());
    }

    private boolean isSufficientBreakBetweenFlights(Flight previousFlight, Flight nextFlight) {
        Duration gap = Duration.between(previousFlight.getClearTime(), nextFlight.getReportTime());
        Duration requiredGap = getRequiredBreak(previousFlight, nextFlight);
        return gap.compareTo(requiredGap) >= 0;
    }

    private Duration getRequiredBreak(Flight previousFlight, Flight nextFlight) {
        if (previousFlight.getFlightDuration().toHours() > 14 || nextFlight.getFlightDuration().toHours() > 14) {
            return Duration.ofHours(18).plusMinutes(30);
        } else {
            return Duration.ofHours(12).plusMinutes(30);
        }
    }


}

/*
 *     |
 *    /\
 *   /\/\
 *
 *
 * */