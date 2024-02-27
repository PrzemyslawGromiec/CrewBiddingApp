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
            processSinglePeriod(generatedPeriod, false);
        }
        return requestService.getFlightRequests();
    }

    private void processSinglePeriod(Period period, boolean showAllDurations) {
        System.out.println("Processing period: " + period);

        List<Flight> flightsForCurrentPeriod = flightService.getFlightsForPeriod(period,showAllDurations);
        if (flightsForCurrentPeriod.isEmpty()) {
            System.out.println("No flights for this period");
            return;
        }

        List<Flight> availableFlights = requestService.filterBuffer(flightsForCurrentPeriod, period);

        ChooseFlightResult result = periodController.chooseFlight(requestService, availableFlights);

        if (result.getStatus() == ChooseFlightResult.Status.PERIOD_SKIPPED) {
            System.out.println("No flight selected for this period.");
            return;
        }

        if (result.getStatus() == ChooseFlightResult.Status.SHOW_ALL_DURATIONS) {
            processSinglePeriod(period, true);
            return;
        }

        List<Period> newCreatedPeriods = period.splitPeriodAroundSelectedFlight(result.getFlight().orElseThrow());

        processSinglePeriod(newCreatedPeriods.get(0),true);
        processSinglePeriod(newCreatedPeriods.get(1),true);
    }
}

