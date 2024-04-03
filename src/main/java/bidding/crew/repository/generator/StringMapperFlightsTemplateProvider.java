package bidding.crew.repository.generator;

import bidding.crew.entity.AircraftType;

import java.time.DayOfWeek;
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
                .collect(Collectors.toList());
    }

    Optional<FlightTemplate> parseLineToFlight(String line) { //todo zdekomponować
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
        int clearHour = 0;
        int clearMinutes = 0;
        int durationDays = 0;
        if (line.contains("CLEARS")) {
            durationDays = 2;
            String clearTime = line.substring(line.indexOf("@") +1 );
            clearHour = Integer.parseInt(clearTime.substring(0,2));
            clearMinutes = Integer.parseInt(clearTime.substring(3,5));

        }

        String[] parts = line.split(" ");
        String numOfDays = "";
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.equalsIgnoreCase("DAY")) {
                numOfDays = parts[i-1];
                //System.out.println(line);
                durationDays = getNumOfDays(numOfDays);
                break;
            }
        }

        //todo: dopisac godziny clear po 18 i przestawic na + 6
        LocalTime clear = LocalTime.of(clearHour,clearMinutes);
        if (durationDays == 0) {
            clear = LocalTime.of(hourInt + 4, minutesInt);
        }

        return Optional.of(new FlightTemplate(flightNr, code, LocalTime.of(hourInt, minutesInt),clear, //todo builder?
                durationDays,daysOfWeek,aircraftType));
    }

    private int getNumOfDays (String days) {
        List<String> listOfNumbers = List.of("TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE");
        int i = listOfNumbers.indexOf(days);
        if (i == -1) {
            throw new UnsupportedOperationException("Not found.");
        }
        return i + 2;
    }
    private List<DayOfWeek> extractDaysOfWeek(String lineFragment) {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        String[] dayStrings = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"}; //todo wyciągnąć z DayOfWeek
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

    private AircraftType extractAircraftType(String line) {
        int aircraftIndex = line.indexOf("(");
        if (aircraftIndex != -1) {
            String aircraftTypeString = line.substring(aircraftIndex + 1, line.indexOf(")")).trim();
            return AircraftType.valueOf(aircraftTypeString.toUpperCase());
        }
        return AircraftType.A320;
    }
}
