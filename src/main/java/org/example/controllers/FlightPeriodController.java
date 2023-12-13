package org.example.controllers;

import org.example.entities.Flight;
import org.example.entities.Period;
import org.example.services.FlightRequestFactory;
import org.example.services.FlightService;

import java.util.List;
import java.util.Scanner;

//TODO: krotki lot moze byc tylko przed dlugim z zachowaniem 16h przerwy
//jesli wybierzemy najpierw dlugi lot, to przerwa przed kolejnym lotem, to min 2 dni
//przerwa pomiedzy krotkimi lotami to 12.5h

//metoda shorten do poprawy
//wyswietlanie gdy w okresie nie miesci sie juz zaden lot


public class FlightPeriodController {
    private Scanner scanner = new Scanner(System.in);
    private FlightService flightService;
    private List<Flight> generatedFlights;

    public FlightPeriodController(FlightService flightService) {
        this.flightService = flightService;
    }

    void chooseFlight(Period generatedPeriod, FlightRequestFactory factory) {
        generatedFlights = flightService.generateFlightsForPeriod(generatedPeriod);
        printListOfFlights(generatedFlights);
        FlightChoice flightChoice = readFlightChoice();

        if (flightChoice.noFlightsChosen()) {
            System.out.println("Moving to the next period.");
            return;
        }

        Flight chosenFlight = generatedFlights.get(flightChoice.getChosenFlightIndex());
        factory.buildRequest(chosenFlight,flightChoice.getPriority());
    }


   private class FlightChoice {
        private int priority;
        private int chosenFlightIndex;
        private boolean valid;
        private String userInput;

        FlightChoice(String userInput) {
            this.userInput = userInput;
            if (noFlightsChosen()) {
                valid = true;
                return;
            }

            try {
                int[] userChoices = validateInput(userInput);
                chosenFlightIndex = userChoices[0] - 1;
                priority = userChoices[1];
                valid = true;
            } catch (IllegalArgumentException e) {
                valid = false;
            }

        }


        private int[] validateInput(String userInput) {

            String[] userValues = userInput.split("/");
            if (userValues.length != 2) {
                throw new IllegalArgumentException("Incorrect number of inputs");
            }
            if (!isNumeric(userValues[0]) || !isNumeric(userValues[1])) {
                throw new IllegalArgumentException("Input is not a number");
            }
            int selectedFlightNum = Integer.parseInt(userValues[0]);
            int selectedFlightPriority = Integer.parseInt(userValues[1]);

            if (selectedFlightPriority < 1 || selectedFlightPriority > 3) {
                throw new IllegalArgumentException("Priority must be value between 1 and 3 only.");
            }
            if (chosenFlightIndex >= 0 && chosenFlightIndex < generatedFlights.size()) {
                throw new IllegalArgumentException("Index out of flights list");
            }
            return new int[]{selectedFlightNum, selectedFlightPriority};
        }

        boolean noFlightsChosen() {
            return "0".equals(userInput.trim());
        }

        public boolean isNotValid() {
            return !valid;
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

        public int getChosenFlightIndex() {
            return chosenFlightIndex;
        }

        public int getPriority() {
            return priority;
        }
    }


    private FlightChoice readFlightChoice() {
        System.out.println("Enter flight number you want to choose and its priority:");
        System.out.println("in the format of 'flight number / priority '.");
        System.out.println("Enter 0 to go to the next period.");
        String userInput = scanner.nextLine();
        FlightChoice flightChoice = new FlightChoice(userInput);
        if (flightChoice.isNotValid()) {
            return readFlightChoice();
        }
        return flightChoice;

    }


    private void printListOfFlights(List<Flight> flights) {
        System.out.println("Flights available in this period: ");
        for (int i = 0; i < flights.size(); i++) {
            Flight generatedFlight = flights.get(i);
            System.out.println("NR: " + (i + 1) + " " + generatedFlight);
        }
    }

}
