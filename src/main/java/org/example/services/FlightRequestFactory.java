package org.example.services;

import org.example.entities.Flight;
import org.example.entities.FlightRequest;

import java.util.ArrayList;
import java.util.List;

public class FlightRequestFactory {
    private List<FlightRequest> requests = new ArrayList<>();
    public void buildRequest(Flight choosedFlight, int priority) {
        FlightRequest flightRequest = new FlightRequest(choosedFlight,priority);
        requests.add(flightRequest);
    }


    public List<FlightRequest> getRequests() {
        return requests;
    }
}
