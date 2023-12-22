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

    public FlightController(FlightService flightService) {
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
        List<Period> periodsToProcess = new ArrayList<>();
        periodsToProcess.add(initialPeriod);

        while (!periodsToProcess.isEmpty()) {
            Period currentPeriod = periodsToProcess.remove(0);
            System.out.println("Processing period: " + currentPeriod);

            List<Flight> flightsForCurrentPeriod = periodController.getFlightsForPeriod(currentPeriod); //todo refactor

            Flight selectedFlight = periodController.chooseFlight(currentPeriod, factory);
           // List<Flight> flightsAvailable = getPossibleNextFlights(selectedFlight, flightsForCurrentPeriod);

            if (selectedFlight == null) {
                System.out.println("No more flights to fill that period.");
                continue;
            }
            List<Period> newCreatedPeriods = currentPeriod.splitPeriodAroundSelectedFlight(selectedFlight);
            for (Period newPeriod : newCreatedPeriods) {
                if (hasAvailableFlightsInThisPeriod(newPeriod, flightsForCurrentPeriod)) {
                    periodsToProcess.add(newPeriod);
                } else {
                    System.out.println("You cannot accommodate any more flights in this period:");
                    System.out.println(newPeriod);
                }
            }
        }
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
