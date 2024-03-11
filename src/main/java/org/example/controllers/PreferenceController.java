package org.example.controllers;

import org.example.entities.AircraftType;
import org.example.entities.Preference;
import org.example.services.PreferencesService;

import java.util.Scanner;

public class PreferenceController {

    //todo: zapisywanie preferences do pliku
    private PreferencesService preferencesService;
    private Scanner scanner = new Scanner(System.in);

    public PreferenceController(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    void updatePreferences() {
        String mainMenuMessage = "Going back to the main menu";
        Preference preference = preferencesService.getModifiablePreferences();
        System.out.println("Your current preferences:");
        System.out.println("Types of the aircraft you are trained on:");
        System.out.println(preference.getTypes() + "\n");
        System.out.println("1. Change min flight time in your search.");
        System.out.println("2. Change max flight time in your search.");
        System.out.println("3. Update your aircraft types.");
        System.out.println("4. " + mainMenuMessage);
        System.out.println("Enter your choice:");
        String userInput = scanner.nextLine();
        if (userInput.equals("1")) {
            System.out.println("Enter new time in hours as a number:");
            preference.setMinFlightHours(scanner.nextInt());

        } else if (userInput.equals("2")) {
            System.out.println("Enter new time in hours as a number:");
            preference.setMaxFlightHours(scanner.nextInt());

        } else if (userInput.equals("3")) {
            System.out.println("Enter new aircraft type: ");
            System.out.println("Available types:");
            for (AircraftType type : AircraftType.values()) {
                System.out.println(type);
            }
            String aircraft = scanner.nextLine();
            preference.addType(AircraftType.valueOf(aircraft));
        } else if (userInput.equals("4")) {
            System.out.println(mainMenuMessage);
        } else {
            System.out.println("Invalid option.");
            System.out.println("Do you want to continue? Type \"yes\" or \"no\".");
            String userChoice = scanner.nextLine();
            if (userChoice.equalsIgnoreCase("yes")) {
                updatePreferences();
            } else {
                System.out.println(mainMenuMessage);
            }
        }
    }
}
