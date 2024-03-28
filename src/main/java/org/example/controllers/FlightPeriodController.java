package org.example.controllers;

import org.example.entities.Flight;
import org.example.services.RequestService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class FlightPeriodController {


    private Scanner scanner = new Scanner(System.in);
    private List<Flight> generatedFlights;
    private static final String LINE_FORMAT = "| %-3s | %-10s | %-4s | %-15s | %-15s | %-8s";

    ChooseFlightResult chooseFlight(RequestService requestService, List<Flight> availableFlights) {
        generatedFlights = availableFlights;
        printListOfFlights(generatedFlights);
        FlightChoice flightChoice = readFlightChoice();

        if (flightChoice.noFlightsChosen()) {
            System.out.println("Moving to the next period.");
            return new ChooseFlightResult(ChooseFlightResult.Status.PERIOD_SKIPPED,Optional.empty());
        }

        if(flightChoice.extendListOfFlights()) {
            System.out.println("Extended list of flights");
            return new ChooseFlightResult(ChooseFlightResult.Status.SHOW_ALL_DURATIONS,Optional.empty());
        }

        Flight chosenFlight = generatedFlights.get(flightChoice.getChosenFlightIndex());
        System.out.println("Chosen flight:");
        System.out.println(chosenFlight);
        displayFlightInfoLimits(chosenFlight);
        requestService.buildRequest(chosenFlight, flightChoice.getPriority());
        return new ChooseFlightResult(ChooseFlightResult.Status.FLIGHT_CHOSEN,Optional.of(chosenFlight));
    }

    private void displayFlightInfoLimits(Flight flight) {
        System.out.println("Required break before next flight: " + flight.calculateBuffer());
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

            if (userInput.equalsIgnoreCase("all")) {
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
            if (chosenFlightIndex < 0  || chosenFlightIndex > generatedFlights.size()) {
                throw new IllegalArgumentException("Index out of flights list");
            }
            return new int[]{selectedFlightNum, selectedFlightPriority};
        }

        boolean noFlightsChosen() {
            return "0".equals(userInput.trim());
        }

        boolean extendListOfFlights() {
            return "all".equalsIgnoreCase(userInput.trim());
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
        System.out.println("Enter \"all\" to extend list of flights");
        String userInput = scanner.nextLine();
        FlightChoice flightChoice = new FlightChoice(userInput);
        if (flightChoice.isNotValid()) {
            return readFlightChoice();
        }

        return flightChoice;

    }


    private void printListOfFlights(List<Flight> flights) {

        System.out.println();
        String headerLinePattern = "--";
        System.out.println(headerLinePattern.repeat(72));
        int totalWidth = 144;
        String availableFlightsText = "AVAILABLE FLIGHTS";
        int leadingSpaces = (totalWidth - availableFlightsText.length()) / 2;
        System.out.printf("%" + leadingSpaces + "s%s%" + leadingSpaces + "s%n", "", availableFlightsText, "");
        System.out.println(headerLinePattern.repeat(72));
        String header = String.format(LINE_FORMAT,
                "ID", "FLT NUMBER", "DEST", "REPORT", "CLEAR", "AIRCRAFT");
        System.out.println(header.repeat(2));

        for (int i = 0; i < flights.size(); i+=2) {
            if (i == flights.size() -1 ) {
                System.out.println(formattingLine(flights.get(i), i));
                break;
            }
            System.out.print(formattingLine(flights.get(i), i));
            System.out.println(formattingLine(flights.get(i+1),i + 1));

        }
        System.out.println();
    }

    private String formattingLine(Flight flight, int i) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM h:mma", Locale.ENGLISH);
        LocalDateTime reportTime = flight.getReportTime();
        LocalDateTime clearTime = flight.getClearTime();
        String formattedReport = reportTime.format(formatter);
        String formattedClear = clearTime.format(formatter);
        return String.format(LINE_FORMAT, i + 1, flight.getFlightNumber(), flight.getAirportCode(),
                formattedReport, formattedClear, flight.getAircraftType());
    }
}
