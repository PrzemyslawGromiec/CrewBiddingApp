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
    private EventRequestFactory eventRequestFactory;
    private FlightController flightController;


    public AppController() {
        this.eventService = new EventService(new EventBinRepository());
        this.flightService = new FlightService();
        this.reportService = new ReportService();
        this.periodFactory = new PeriodFactory();
        this.eventRequestFactory = new EventRequestFactory();
        this.flightController = new FlightController(flightService);
    }

    public void run() {
        int choice = 0;
        System.out.println("Welcome to the crew bidding system!");
        do {
            displayMenu();
            choice = getValidOption();

            switch (choice) {
                case 1 -> {
                    System.out.println(flightService.getFlights().size() + " flights loaded.");
                    List<EventRequest> requests = eventRequestFactory.createRequests(eventService.getEvents());
                    List<Period> generatedPeriods = periodFactory.createPeriodsBetweenRequests(requests);
                    for (Period generatedPeriod : generatedPeriods) {
                        System.out.println(generatedPeriod);
                    }
                    List<FlightRequest> flightRequests = flightController.chooseFlightsForPeriods(generatedPeriods);
                    System.out.println();
                    System.out.println("Your flight requests:");
                    for (FlightRequest flightRequest : flightRequests) {
                        System.out.println(flightRequest);
                    }
                    System.out.println("Your event requests:");
                    for (EventRequest request : requests) {
                        System.out.println(request);
                    }


                    // - wyswietlenie eventRequestow i pytanie uzytkownika czy sie zgadza
                    // - jesli sie nie zgadza, to przerywamy proces i ma mozliwosc poporawienia swoich wolnych
                    // - program generuje okresy i wypiuje je i sprawdza czy wszystko sie zgadza - jesl sie nie zgadza, to tak jak wczesniej
                    // - iterowanie po okresach i pozwalanie uzytkownikowi wybrac od 1 do kilku lotow w ramach jednego okresu
                    // - w trakcie iteracji:
                    //      - period jest uzupelniany lotami
                    //      - upozadkowanie wg ulubionych lub dlugosci lotu itp
                    //      - loty sa prezentowane uzykownikowi
                    //      - uzytkownik wybiera od 1 do kilku lotow i nadaje im priorytety (wybieranie za pomoca numeru pozycji)
                    //      - kazdy wybor uzytkownika powoduje stworzenie FlightRequest dla wybranego przez niego lotu i priorytet
                    // - skladany jest koncowy raport i przeliczane sa priorytety na punkty
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
