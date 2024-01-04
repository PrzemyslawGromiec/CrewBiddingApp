package org.example.services;

import org.example.entities.EventRequest;
import org.example.entities.Flight;
import org.example.entities.FlightRequest;
import org.example.entities.Period;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RequestService {
    private FlightRequestFactory factory = new FlightRequestFactory();
    private List<EventRequest> eventRequests = new ArrayList<>();


    public List<FlightRequest> getRequests() {
        return factory.getRequests();
    }

    public void buildRequest(Flight chosenFlight, int priority) {
        factory.buildRequest(chosenFlight, priority);
    }

    public List<Flight> filterBuffer(List<Flight> flightsForCurrentPeriod, Period period) {
        //dla każdego lotu
        //sprawdzam wszystkie loty z requestów
        //wyliczam ich pełny czas + ich bufor / pobieram - flight requests
        //sprawdzam czy przeglądany lot koliduje z tym okresem
        //jeśli koliduje to nie dodaje go do finalnej listy
        List<Flight> availableFlights = new ArrayList<>();
        flightsForCurrentPeriod = flightsAfterDayOff(flightsForCurrentPeriod, period);

        for (Flight flight : flightsForCurrentPeriod) {
            boolean isValid = true;
            for (FlightRequest chosenFlightRequest : factory.getRequests()) {
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
