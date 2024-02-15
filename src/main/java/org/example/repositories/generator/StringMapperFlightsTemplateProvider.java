package org.example.repositories.generator;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.entities.Period;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

class StringMapperFlightsTemplateProvider implements FlightsTemplateProvider {
    private List<String> lines;

    public StringMapperFlightsTemplateProvider(List<String> lines) {
        this.lines = lines;
    }


    @Override
    public List<FlightTemplate> provideFlights() {
        return lines.stream()
                .map(line -> parseLineToFlight(line).orElse(null))
                .filter(flight -> flight != null)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    Optional<FlightTemplate> parseLineToFlight(String line) {
        if (!line.startsWith("✓")) {
            return Optional.empty();
        }
        String hour = line.substring(2, 4);
        int hourInt = Integer.parseInt(hour);
        String minutes = line.substring(5, 7);
        int minutesInt = Integer.parseInt(minutes);
        String flightNr = line.substring(15, 21);
        String code = line.substring(22, 25);

        List<DayOfWeek> daysOfWeek = extractDaysOfWeek(line);
        AircraftType aircraftType = extractAircraftType(line);
        //int durationDays = calculateDuration(line);
        int clearHour = 0;
        int clearMinutes = 0;
        int durationDays = 0;
        if (line.contains("CLEARS")) {
            durationDays = 2;
            String clearTime = line.substring(line.indexOf("@") +1 );
            clearHour = Integer.parseInt(clearTime.substring(0,2));
            clearMinutes = Integer.parseInt(clearTime.substring(3,5));

        }
        //✓ 14.55 REPORT BA0189 EWR (B787) THREE DAY TRIP CLEARS @10.10AM
        String[] parts = line.split(" ");
        String numOfDays = "";
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.equalsIgnoreCase("DAY")) {
                numOfDays = parts[i-1];
                System.out.println(line);
                durationDays = getNumOfDays(numOfDays);
                break;
            }
        }

        //todo: dopisac godziny clear po 18 i przestawic na + 6
        LocalTime clear = LocalTime.of(clearHour,clearMinutes);
        if (durationDays == 0) {
            clear = LocalTime.of(hourInt + 4, minutesInt);
        }

        return Optional.of(new FlightTemplate(flightNr, code, LocalTime.of(hourInt, minutesInt),clear, durationDays,daysOfWeek,aircraftType));
    }

    private static int getNumOfDays (String days) {
        List<String> listOfNumbers = List.of("TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE");
        int i = listOfNumbers.indexOf(days);
        if (i == -1) {
            throw new UnsupportedOperationException("Not found.");
        }
        return i + 2;
    }
    private static List<DayOfWeek> extractDaysOfWeek(String lineFragment) {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        String[] dayStrings = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        for (String dayString : dayStrings) {
            if (lineFragment.contains(dayString)) {
                daysOfWeek.add(DayOfWeek.valueOf(dayString.toUpperCase()));
            }
        }

        if (daysOfWeek.isEmpty()) {
            daysOfWeek.addAll(EnumSet.allOf(DayOfWeek.class));
        }
        return daysOfWeek;
    }

    private static AircraftType extractAircraftType(String line) {
        int aircraftIndex = line.indexOf("(");
        if (aircraftIndex != -1) {
            String aircraftTypeString = line.substring(aircraftIndex + 1, line.indexOf(")")).trim();
            return AircraftType.valueOf(aircraftTypeString.toUpperCase());
        }
        return AircraftType.A320;
    }

    private static int calculateDuration(String line) {
        // Check if "DAY TRIP" is present in the line
        if (!line.contains("DAY TRIP")) {
            // If "DAY TRIP" is not present, return 0
            return 0;
        }

        // Extract the portion of the line after "DAY TRIP" or "CLEARS"
        String durationString = line.contains("CLEARS") ?
                line.substring(line.indexOf("DAY TRIP") + 8, line.indexOf("CLEARS")) :
                line.substring(line.indexOf("DAY TRIP") + 8);
       // System.out.println("Duration string: " + durationString); // Debug print

        // Split the duration string by whitespace and extract the first token
        String[] tokens = durationString.trim().split("\\s+");
        if (tokens.length > 0 && tokens[0].matches("\\d+")) { // Check if the first token is a number
            return Integer.parseInt(tokens[0]); // Parse the number of days
        } else {
            return 0; // Return 0 if the duration string is not in the expected format
        }
    }


    //✓ 11.55 REPORT BA0225 MSY (787) FIVE DAY TRIP ON SATURDAY, FOUR DAY TRIP ON TUESDAY, THREE DAY TRIP THURSDAY, FRIDAY CLEARS @11.10AM
}
