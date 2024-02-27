package org.example.controllers;

import org.example.entities.AircraftType;
import org.example.entities.Preference;

import java.util.List;
import java.util.Scanner;

public class PreferenceController {
    private Preference preference = new Preference();
    private Scanner scanner = new Scanner(System.in);

    void updatePreferences() {
        System.out.println("Your current preferences:");
        System.out.println(preference.getMaxFlightHours());
        System.out.println(preference.getMinFlightHours());
        System.out.println(preference.getTypes());
        System.out.println("1. Change min flight time in your search.");
        System.out.println("2. Change max flight time in your search.");
        System.out.println("3. Update your aircraft types.");
        String userInput = scanner.nextLine();
        if (userInput.equals("1")) {
            System.out.println("Enter new time in hours as a number:");
            preference.setMinFlightHours(scanner.nextInt());

        } else if (userInput.equals("2")) {
            System.out.println("Enter new time in hours as a number:");
            preference.setMaxFlightHours(scanner.nextInt(scanner.nextInt()));

        } else if (userInput.equals("3")) {
            System.out.println("Enter new aircraft type: ");
            System.out.println("Available types:");
            for (AircraftType type : AircraftType.values()) {
                System.out.println(type);
            }
            String aircraft = scanner.nextLine();
            preference.addType(AircraftType.valueOf(aircraft));

        } else {
            System.out.println("Invalid option.");
        }
        updatePreferences();
    }
}
