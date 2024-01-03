package org.example.services;

import org.example.entities.Flight;
import org.example.entities.FlightRequest;

import java.util.ArrayList;
import java.util.List;

public class FlightRequestFactory {
    private List<FlightRequest> requests = new ArrayList<>();

    public FlightRequestFactory() {
    }

    public void buildRequest(Flight chosenFlight, int priority) {
        FlightRequest flightRequest = new FlightRequest(chosenFlight,priority);
        requests.add(flightRequest);
    }

    public List<FlightRequest> getRequests() {
        return requests;
    }
}
