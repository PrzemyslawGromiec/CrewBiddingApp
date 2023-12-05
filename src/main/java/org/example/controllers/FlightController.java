package org.example.controllers;

import org.example.entities.FlightRequest;
import org.example.entities.Period;
import org.example.services.FlightRequestFactory;
import org.example.services.FlightService;
import org.example.entities.Flight;

import java.util.List;
import java.util.Scanner;

public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public List<FlightRequest> chooseFlightsForPeriods(List<Period> generatedPeriods) {
        FlightRequestFactory factory = new FlightRequestFactory();
        for (Period generatedPeriod : generatedPeriods) {
            chooseFlights(generatedPeriod, factory);
        }

        List<FlightRequest> requests = factory.getRequests();
        return requests;
    }

    private void chooseFlights(Period generatedPeriod, FlightRequestFactory factory) {
        int userChoice;
        do {
            Scanner scanner = new Scanner(System.in);
            List<Flight> generatedFlights = flightService.generateFlightsForPeriod(generatedPeriod);
            System.out.println("In this period: " + generatedPeriod + " flights available: ");
            for (int i = 0; i < generatedFlights.size(); i++) {
                Flight generatedFlight = generatedFlights.get(i);
                System.out.println("NR: " + (i + 1) + " " + generatedFlight);
            }
            System.out.println("Enter flight number you want to choose:");
            System.out.println("Enter 0 to go to the next period.");
            userChoice = scanner.nextInt();
            if (userChoice==0) {
                System.out.println("Moved to next period...");
              continue;
            }

            Flight choosedFlight = generatedFlights.get(userChoice - 1);
            int priority = 3;
            factory.buildRequest(choosedFlight, priority);
            //TODO: zapytac o prirytet do lotu lub scalic z mozliwoscia wyboru lotu np nr1.p3

        } while (userChoice != 0);

    }


}
