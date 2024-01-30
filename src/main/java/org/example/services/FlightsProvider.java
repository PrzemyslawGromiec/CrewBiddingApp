package org.example.services;

import org.example.entities.Flight;

import java.util.List;

public interface FlightsProvider {
    List<Flight> provideFlights();
}
