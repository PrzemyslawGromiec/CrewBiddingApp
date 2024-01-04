package org.example.controllers;

import org.example.entities.FlightRequest;
import org.example.entities.Period;
import org.example.services.FlightService;
import org.example.entities.Flight;
import org.example.services.RequestService;

import java.util.List;
import java.util.Optional;


public class FlightController {
    private FlightPeriodController periodController;
    private FlightService flightService;
    private RequestService requestService;

    public FlightController(FlightService flightService, RequestService requestService) {
        this.flightService = flightService;
        periodController = new FlightPeriodController();
        this.requestService = requestService;
    }

    public List<FlightRequest> chooseFlightsForPeriods(List<Period> generatedPeriods) {

        for (Period generatedPeriod : generatedPeriods) {
            processSinglePeriod(generatedPeriod);
        }
        return requestService.getRequests();
    }

    private void processSinglePeriod(Period period) {
        System.out.println("Processing period: " + period);

        List<Flight> flightsForCurrentPeriod = flightService.getFlightsForPeriod(period);
        if (flightsForCurrentPeriod.isEmpty()) {
            System.out.println("No flights for this period");
            return;
        }

        List<Flight> availableFlights = requestService.filterBuffer(flightsForCurrentPeriod, period);

        Optional<Flight> selectedFlight = periodController.chooseFlight(requestService, availableFlights);

        //selectedFlight.ifPresent(flight -> System.out.println("No flight selected"));
        if (selectedFlight.isEmpty()) {
            System.out.println("No flight selected for this period.");
            return;
        }

        List<Period> newCreatedPeriods = period.splitPeriodAroundSelectedFlight(selectedFlight.orElseThrow());

        processSinglePeriod(newCreatedPeriods.get(0));
        processSinglePeriod(newCreatedPeriods.get(1));
    }
}

