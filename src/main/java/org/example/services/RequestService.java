package org.example.services;

import org.example.entities.*;
import org.example.general.Time;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RequestService {
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventRequestFactory eventFactory = new EventRequestFactory(Time.getTime());
    private EventService eventService;

    public RequestService(EventService eventService) {
        this.eventService = eventService;
    }


    public List<FlightRequest> getFlightRequests() {
        return flightFactory.getRequests();
    }
    public List<EventRequest> getEventRequests() {
        return eventFactory.createRequests(eventService.getEvents());
    }
    public void buildRequest(Flight chosenFlight, int priority) {
        flightFactory.buildRequest(chosenFlight, priority);
    }

    //todo: stream
    public List<Flight> filterBuffer(List<Flight> flightsForCurrentPeriod) {
        List<Flight> availableFlights = new ArrayList<>();

        for (Flight flight : flightsForCurrentPeriod) {
            boolean isValid = true;
            for (FlightRequest chosenFlightRequest : flightFactory.getRequests()) {
                boolean validFlightInPeriod = chosenFlightRequest.getFlight().isValidFlightInPeriod(flight);
                if (!validFlightInPeriod) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                availableFlights.add(flight);
            }
        }
        return availableFlights;
    }

}
