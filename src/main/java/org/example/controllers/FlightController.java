package org.example.controllers;

import org.example.entities.FlightRequest;
import org.example.entities.Period;
import org.example.services.FlightService;
import org.example.entities.Flight;
import org.example.services.RequestService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class FlightController {
    private FlightPeriodController periodController;
    private FlightService flightService;
    private RequestService requestService;
    private Scanner scanner = new Scanner(System.in);

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


    //Period: 100 pasuje 300 full
    //Period: 0 pasuje 150 full
    //Period: 0 pasuje 0 full


    //Period: 0 / wiecej




    private void processSinglePeriod(Period period, boolean showAllDurations) {
        System.out.println("Processing period: " + period);

        List<Flight> flightsForCurrentPeriod = flightService.getFlightsForPeriod(period,showAllDurations);
        List<Flight> availableFlights = requestService.filterBuffer(flightsForCurrentPeriod);
        //flight service daja nam albo ograniczone loty albo wszystkie
        String noFlightsMessage = "No flights for this period";
        if (availableFlights.isEmpty()) {
            if (showAllDurations) {
                System.out.println(noFlightsMessage);
              return;
            }

            flightsForCurrentPeriod = flightService.getFlightsForPeriod(period, true);
            availableFlights = requestService.filterBuffer(flightsForCurrentPeriod);
            if (availableFlights.isEmpty()) {
                System.out.println(noFlightsMessage);
                return;
            }
            System.out.println("Available " + flightsForCurrentPeriod.size() + " flights for extended criteria." +
                    "Do you want to consider them?");
            String userChoice = scanner.nextLine();
            if (userChoice.equalsIgnoreCase("no")) {
                return;
            }

            //jeśli jest empty to może są jeszcze ukryte loty które chcielibysmy pokazać, czyli te na all duration =true
        }

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

        processSinglePeriod(newCreatedPeriods.get(0),false);
        processSinglePeriod(newCreatedPeriods.get(1),false);
    }
}

