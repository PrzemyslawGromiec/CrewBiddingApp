package org.example.services;

import org.example.entities.EventRequest;
import org.example.entities.Flight;
import org.example.entities.Period;

import java.util.List;

public class PeriodService {
    private FlightService flightService;
    private PeriodFactory periodFactory;
    private RequestFactory requestFactory;
    private EventService eventService;


    public PeriodService(FlightService flightService, PeriodFactory periodFactory, RequestFactory requestFactory, EventService eventService) {
        this.flightService = flightService;
        this.periodFactory = periodFactory;
        this.requestFactory = requestFactory;
        this.eventService = eventService;
    }

    public List<Period> generatePeriodsWithFlights() {
        List<EventRequest> requests = requestFactory.createRequests(eventService.getEvents());

        List<Period> periods = periodFactory.createPeriodsBetweenRequests(requests);

        // Wypełnienie okresów dostępnymi lotami
        for (Period period : periods) {
            //List<Flight> flights = flightService.findFlightsForPeriod(period);
        }

        return periods;
    }
}
