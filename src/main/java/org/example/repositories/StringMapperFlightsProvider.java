package org.example.repositories;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.services.FlightService;
import org.example.services.FlightsProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringMapperFlightsProvider implements FlightsProvider {
    private List<String> lines;

    public StringMapperFlightsProvider(List<String> lines) {
        this.lines = lines;
    }


    @Override
    public List<Flight> provideFlights() {
        return lines.stream()
                .map(this::parseLineToFlight)
                .collect(Collectors.toList());
    }

    Flight parseLineToFlight(String line) {
        String hour = line.substring(2, 4);
        int hourInt = Integer.parseInt(hour);
        String minutes = line.substring(5, 7);
        int minutesInt = Integer.parseInt(minutes);
        String flightNr = line.substring(15, 21);
        String code = line.substring(22, 25);

        Flight flightFromLine = new Flight(flightNr, code, LocalDateTime.now().withHour(hourInt).withMinute(minutesInt),
                LocalDateTime.now().withHour(23).withMinute(59), AircraftType.A320);

        return flightFromLine;
    }
}
