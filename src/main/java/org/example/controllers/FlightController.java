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
import java.util.Optional;
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

    private void processSinglePeriod(Period period, FlightRequestFactory factory) {
        System.out.println("Processing period: " + period);

        List<Flight> flightsForCurrentPeriod = flightService.getFlightsForPeriod(period);
        if (flightsForCurrentPeriod.isEmpty()) {
            System.out.println("No flights for this period");
            return;
        }

        Optional<Flight> selectedFlight = periodController.chooseFlight(period, factory);

        //selectedFlight.ifPresent(flight -> System.out.println("No flight selected"));
        if (selectedFlight.isEmpty()) {
            System.out.println("No flight selected for this period.");
            return;
        }

        List<Period> newCreatedPeriods = period.splitPeriodAroundSelectedFlight(selectedFlight.orElseThrow());

        processSinglePeriod(newCreatedPeriods.get(0), factory);
        processSinglePeriod(newCreatedPeriods.get(1),factory);
    }


}

/*
 *     |
 *    /\
 *   /\/\
 *
 *
 * */