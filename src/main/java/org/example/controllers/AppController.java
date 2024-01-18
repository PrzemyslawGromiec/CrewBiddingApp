package org.example.controllers;

import org.example.entities.*;
import org.example.repositories.EventBinRepository;
import org.example.services.*;

import java.util.List;
import java.util.Scanner;

public class AppController {

    private Scanner scanner = new Scanner(System.in);
    private EventService eventService;
    private FlightService flightService;
    private ReportService reportService;
    private PeriodFactory periodFactory;
    private FlightController flightController;
    private RequestService requestService;


    public AppController() {
        this.eventService = new EventService(new EventBinRepository());
        this.flightService = new FlightService();
        this.periodFactory = new PeriodFactory();
        this.requestService = new RequestService(eventService);
        this.flightController = new FlightController(flightService, requestService);
        this.reportService = new ReportService();
    }

    public void run() {
        int choice;
        System.out.println("Welcome to the crew bidding system!");
        do {
            displayMenu();
            choice = getValidOption();

            switch (choice) {
                case 1 -> {
                    System.out.println(flightService.getFlights().size() + " flights loaded.");
                    List<EventRequest> eventRequests = requestService.getEventRequests();
                    List<Period> generatedPeriods = periodFactory.createPeriodsBetweenRequests(eventRequests);
                    for (Period generatedPeriod : generatedPeriods) {
                        System.out.println(generatedPeriod);
                    }
                    System.out.println();
                    List<FlightRequest> flightRequests = flightController.chooseFlightsForPeriods(generatedPeriods);
                    System.out.println();
                    System.out.println("Your flight eventRequests:");
                    for (FlightRequest flightRequest : flightRequests) {
                        System.out.println(flightRequest);
                    }
                    System.out.println("Your event eventRequests:");
                    for (EventRequest request : eventRequests) {
                        System.out.println(request);
                    }

                    Report report = reportService.finalizedReport(flightRequests, eventRequests);
                    displayFinalReport(report);

                }
                case 2 -> modifyRequests();
                case 3 ->
                    //your preferences and requirements
                        System.out.println("Add your aircraft types");
                case 4 -> System.out.println("Bye bye!");
                default -> System.out.println("Incorrect choice. Try again.");
            }

        } while (choice != 4);

    }

    private void displayFinalReport(Report report) {
        List<Request> requests = report.getRequests();
        for (Request request : requests) {
            System.out.println("Date:" + request.getStartTime() + " Type:" + request.getType() + " Points:" + request.getPoints());
        }
    }

    public void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1. Help me to create my schedule.");
        System.out.println("2. Modify (add/delete) your requests.");
        System.out.println("3. Add your current trainings.");
        System.out.println("4. End the application.");
    }


    private void modifyRequests() {
        eventService.showEvents();
        System.out.println("Type 'add' to add even, 'delete' to remove event, 'exit' to go back.");
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("add")) {
            eventService.addAllEvents();
        } else if (userInput.equalsIgnoreCase("delete")) {
            eventService.deleteById();
        } else if (userInput.equalsIgnoreCase("exit")) {
            System.out.println("Taking you back to the main menu.");
        } else {
            System.out.println("Invalid input. Type 'add' or 'delete' only.");
            modifyRequests();
        }
    }

    public boolean isUserInputValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public int getValidOption() {
        String userInput;
        while (true) {
            System.out.println("Enter your choice:");
            userInput = scanner.nextLine();
            if (isUserInputValid(userInput)) {
                return Integer.parseInt(userInput);
            } else {
                System.out.println("Invalid input. Please enter a number!");
            }
        }
    }
}
