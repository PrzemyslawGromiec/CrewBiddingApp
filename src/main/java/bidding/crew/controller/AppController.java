package bidding.crew.controller;

import bidding.crew.entity.*;
import bidding.crew.repository.EventBinRepository;
import bidding.crew.repository.generator.FlightGeneratorFacade;
import bidding.crew.repository.generator.Source;
import bidding.crew.service.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AppController {

    private final EventService eventService;
    private final FlightService flightService;
    private final ReportService reportService;
    private final PeriodFactory periodFactory;
    private final FlightController flightController;
    private final RequestService requestService;
    private final PreferenceController preferenceController;
    private final Scanner scanner = new Scanner(System.in);


    public AppController() throws FileNotFoundException {
        PreferencesService preferencesService = new PreferencesService();
        eventService = new EventService(new EventBinRepository());
        flightService = new FlightService(FlightGeneratorFacade.Factory.createFlightFacade(Source.FILE), preferencesService);
        periodFactory = new PeriodFactory();
        requestService = new RequestService(eventService);
        flightController = new FlightController(flightService, requestService);
        reportService = new ReportService();
        preferenceController = new PreferenceController(preferencesService);
    }

    public void runMenu() {
        int choice;
        System.out.println("Welcome to the crew bidding system!");
        do {
            displayMenu();
            choice = getValidOption();
            executeChoice(choice);
        } while (choice != 4);
    }

    public void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1. Help me to create my schedule.");
        System.out.println("2. Modify (add/delete) your requests.");
        System.out.println("3. Update your preferences (aircraft types, min, max flight time.");
        System.out.println("4. End the application.");
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 1 -> createSchedule();
            case 2 -> modifyRequests();
            case 3 -> preferenceController.updatePreferences();
            case 4 -> System.out.println("Bye bye!");
            default -> System.out.println("Incorrect choice. Try again.");
        }
    }

    private void createSchedule() {
        flightService.applyAircraftTypePreference();
        System.out.println(flightService.getFlights().size() + " flights loaded.");

        List<EventRequest> eventRequests = requestService.getEventRequests();
        List<Period> generatedPeriods = getGeneratedPeriods(eventRequests);
        System.out.println();
        List<FlightRequest> flightRequests = flightController.chooseFlightsForPeriods(generatedPeriods);

        List<Request> allRequests = new ArrayList<>(eventRequests);
        allRequests.addAll(flightRequests);

        System.out.println("Your requests: ");
        displayRequests(allRequests);

        Report report = reportService.finalizedReport(flightRequests, eventRequests);
        displayFinalReport(report);
    }

    private List<Period> getGeneratedPeriods(List<EventRequest> eventRequests) {
        List<Period> generatedPeriods = periodFactory.createPeriodsBetweenRequests(eventRequests);
        System.out.println("Calculated periods:");
        for (Period generatedPeriod : generatedPeriods) {
            System.out.println(generatedPeriod);
        }
        return generatedPeriods;
    }

    private void displayRequests(List<Request> requests) {
        for (Request request : requests) {
            System.out.println(request);
        }
    }

    private void displayFinalReport(Report report) {
        List<Request> requests = report.getRequests();
        List<Request> sortedByPoints = requests.stream()
                .sorted(Comparator.comparing(Request::getPoints).reversed()).toList();
        System.out.println("\nPrinting final report:");
        for (Request request : sortedByPoints) {
            System.out.println(request);
        }
    }

   private void modifyRequests() {
       eventService.showEvents();
       System.out.println("Enter corresponding number: \n" +
               "1. Add event \n2. Delete event \n3. Main menu");
       switch (scanner.nextInt()) {
           case 1 -> eventService.addAllEvents();
           case 2 -> eventService.deleteById();
           case 3 -> System.out.println("Going back to the main menu.");
           default -> System.out.println("Incorrect choice. Try again.");
       }
   }

    private boolean isUserInputValid(String input) {
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

    private int getValidOption() {
        String userInput;
        while (true) {
            System.out.println("Enter your choice:");
            userInput = scanner.nextLine();
            if (isUserInputValid(userInput)) {
                return Integer.parseInt(userInput);
            } else {
                System.out.println("Invalid input. Please enter a number! check!!");
            }
        }
    }
}
