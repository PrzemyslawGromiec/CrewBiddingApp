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
    private Scanner scanner = new Scanner(System.in);

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
        String userInput;
        int[] userChoices;
        do {

            List<Flight> generatedFlights = flightService.generateFlightsForPeriod(generatedPeriod);
            System.out.println("In this period: " + generatedPeriod + " flights available: ");
            for (int i = 0; i < generatedFlights.size(); i++) {
                Flight generatedFlight = generatedFlights.get(i);
                System.out.println("NR: " + (i + 1) + " " + generatedFlight);
            }
            System.out.println("Enter flight number you want to choose and its priority:");
            System.out.println("in the format of 'flight number / priority '.");
            System.out.println("Enter 0 to go to the next period.");

            userInput = scanner.nextLine();
            if ("0".equals(userInput.trim())) {
                System.out.println("Moving to the next period.");
                break;
            }

            userChoices = processInput(userInput);
            if (userChoices.length == 2) {
                int chosenFlightIndex = userChoices[0] - 1;
                int priority = userChoices[1];

                if (chosenFlightIndex >= 0 && chosenFlightIndex < generatedFlights.size()) {
                    Flight chosenFlight = generatedFlights.get(userChoices[0]);
                    factory.buildRequest(chosenFlight, priority);
                    generatedPeriod.shortenPeriod(chosenFlight);
                } else {
                    System.out.println("Invalid flight number.");
                    System.out.println("Choose a number between 1 and " + generatedFlights.size());
                    System.out.println("remember to enter - 'flight number / flight priority' \n");
                }
            }

        } while (true);

    }

    private int[] processInput(String userInput) {

        String[] userValues = userInput.split("/");
        if (userValues.length == 2 && isNumeric(userValues[0]) &&
                isNumeric(userValues[1])) {
            int selectedFlightNum = Integer.parseInt(userValues[0]);
            int selectedFlightPriority = Integer.parseInt(userValues[1]);

            if (selectedFlightPriority >= 1 && selectedFlightPriority <= 3) {
                return new int[]{selectedFlightNum, selectedFlightPriority};
            } else {
                System.out.println("Priority must be value between 1 and 3 only.");
            }
        }
        return new int[0];
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Value is not a number!");
            return false;
        }
    }


}
