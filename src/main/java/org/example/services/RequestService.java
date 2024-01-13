package org.example.services;

import org.example.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RequestService {
    private FlightRequestFactory flightFactory = new FlightRequestFactory();
    private EventRequestFactory eventFactory = new EventRequestFactory();


    public List<FlightRequest> getFlightRequests() {
        return flightFactory.getRequests();
    }

    public List<EventRequest> getEventRequests() {
        return eventFactory.getRequests();
    }

    public void buildRequest(Flight chosenFlight, int priority) {
        flightFactory.buildRequest(chosenFlight, priority);
    }

    public List<Flight> filterBuffer(List<Flight> flightsForCurrentPeriod, Period period) {
        List<Flight> availableFlights = new ArrayList<>();
        flightsForCurrentPeriod = flightsAfterDayOff(flightsForCurrentPeriod, period);

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

    //you can start flying min at 6AM after day off
    public List<Flight> flightsAfterDayOff(List<Flight> flights, Period period) {

        LocalDateTime six = period.getStartTime().withHour(6).withMinute(0).withSecond(0);
        return flights.stream()
                .filter(flight -> flight.getReportTime().isAfter(six))
                .sorted(Comparator.comparing(Flight::getReportTime))
                .collect(Collectors.toList());
    }



}
