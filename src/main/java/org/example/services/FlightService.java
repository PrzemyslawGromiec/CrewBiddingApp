package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();

    public FlightService() {
        flights.add(new Flight("123","CDG", LocalDateTime.of(2023, 12,10,10,0),
                LocalDateTime.of(2023,12,10,17,30), AircraftType.A320 ) );
        flights.add(new Flight("143","BER", LocalDateTime.of(2023, 12,10,10,0),
                LocalDateTime.of(2023,12,9,15,30), AircraftType.A320 ));
        flights.add(new Flight("153","WAW", LocalDateTime.of(2023, 12,10,11,0),
                LocalDateTime.of(2023,12,10,17,30), AircraftType.A320 ));
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
