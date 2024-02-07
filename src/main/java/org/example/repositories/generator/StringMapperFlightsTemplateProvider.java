package org.example.repositories.generator;

import org.example.entities.AircraftType;
import org.example.entities.Flight;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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

        return Optional.of(new FlightTemplate(flightNr, code, LocalTime.of(hourInt, minutesInt)));
    }
}
