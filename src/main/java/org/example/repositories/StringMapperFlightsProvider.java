package org.example.repositories;

import org.example.entities.Flight;
import org.example.services.FlightsProvider;

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

    private Flight parseLineToFlight (String line) {
        return null;
    }
}
