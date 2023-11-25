package org.example.controllers;

import org.example.entities.Flight;
import org.example.repositories.EventBinRepository;
import org.example.repositories.EventRepository;
import org.example.services.EventService;
import org.example.services.FlightService;
import org.example.services.ReportService;
import org.example.services.RequestFactory;

import java.util.List;
import java.util.Scanner;

public class AppController {

    private Scanner scanner = new Scanner(System.in);
    private EventService eventService;
    private FlightService flightService;
    private ReportService reportService;


    public AppController() {
        this.eventService = new EventService(new EventBinRepository());
        this.flightService = new FlightService();
        this.reportService = new ReportService();
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

                    // - wyswietlenie eventRequestow i pytanie uzytkownika czy sie zgadza
                    // - jesli sie nie zgadza, to przerywamy proces i ma mozliwosc poporawienia swoich wolnych
                    // - program generuje okresy i wypiuje je i sprawdza czy wszystko sie zgadza - jesl sie nie zgadza, to tak jak wczesniej
                    // - wypelnienie okresow lotami
                    // - iterowanie po okresach i pozwalanie uzytkownikowi wybrac od 1 do kilku lotow w ramach jednego okresu
                    // - na bazie wyborow uzytkownika w tym priority beda kreowane na koniec requesty lotow
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
        System.out.println("Type 'add' to add even or 'delete' to remove event.");
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("add")) {
            eventService.addAllEvents();
        } else if (userInput.equalsIgnoreCase("delete")) {
            eventService.deleteById();
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
