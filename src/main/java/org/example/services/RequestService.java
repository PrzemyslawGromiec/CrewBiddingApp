package org.example.services;

import org.example.entities.EventRequest;
import org.example.entities.Flight;
import org.example.entities.FlightRequest;
import org.example.entities.Period;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RequestService {
    private FlightRequestFactory factory = new FlightRequestFactory();
    private List<EventRequest> eventRequests = new ArrayList<>();


    public List<FlightRequest> getRequests() {
        return factory.getRequests();
    }

    public void buildRequest(Flight chosenFlight, int priority) {
        factory.buildRequest(chosenFlight, priority);
    }

    public List<EventRequest> getEventRequests() {
        return eventRequests;
    }

    public void addEventRequest(EventRequest eventRequest) {
        eventRequests.add(eventRequest);
    }


    public List<Flight> filterBuffer(List<Flight> flightsForCurrentPeriod, Period period) {
        //dla każdego lotu
        //sprawdzam wszystkie loty z requestów
        //wyliczam ich pełny czas + ich bufor / pobieram
        //sprawdzam czy przeglądany lot koliduje z tym okresem
        //jeśli koliduje to nie dodaje go do finalnej listy
        List<Flight> availableFlights = new ArrayList<>();

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

    public boolean isFlightAcceptable(Flight flight, Period currentPeriod, List<EventRequest> nextEventRequest) {
        if (flight.getFlightDuration().compareTo(Duration.ofHours(14)) < 0) {
            return isValidShortFlight(flight, currentPeriod, nextEventRequest);
        } else {
            return isValidLongFlight(flight);
        }
    }

    private boolean isValidLongFlight(Flight flight) {
        Duration minLongFlightBreak = Duration.ofDays(2);

        LocalDate landingDate = flight.getClearTime().toLocalDate();

        LocalDate today = LocalDate.now();
        if (landingDate.plusDays(2).isAfter(today)) {
            return false;
        }

        return true;
    }

    private boolean isValidShortFlight(Flight flight, Period currentPeriod, List<EventRequest> nextEventRequest) {
        // Sprawdza warunki dla lotów krótkich
        // 1. Jeśli lot jest pierwszy po dniu wolnym, nie może zacząć się przed godziną 6:00
        // 2. Minimalna przerwa między lotami krótkimi to 12.5 godziny
        // 3. Minimalna przerwa między lotem krótkim a długim to 18.5 godziny
        // Tutaj należy dodać logikę do sprawdzania tych warunków
        if (isFirstFlightAfterDayOff(flight, currentPeriod, nextEventRequest)) {
            LocalTime earliestStartTime = LocalTime.of(6, 0);
            if (flight.getReportTime().toLocalTime().isBefore(earliestStartTime)) {
                return false;
            }
        }
        if (!hasSufficientShortFlightGap(flight)) {
            return false;
        }
        if (!hasSufficientLongFlightGap(flight, currentPeriod)) {
            return false;
        }

        return true;
    }

    private boolean hasSufficientLongFlightGap(Flight flight, Period currentPeriod) {
        Duration minLongFlightGap = Duration.ofHours(18).plusMinutes(30);

        LocalTime endOfCurrentPeriod = currentPeriod.getEndTime().toLocalTime();

        List<Flight> availableFlights = getRequests().stream()
                .map(FlightRequest::getFlight)
                .toList();

        for (Flight otherFlight : availableFlights) {
            // Pomijaj ten sam lot
            if (flight.equals(otherFlight)) {
                continue;
            }

            // Jeśli obecny lot kończy się po godzinie, z którą koliduje
            if (flight.getClearTime().toLocalTime().isAfter(endOfCurrentPeriod)) {
                // Oblicz różnicę czasu między końcem obecnego lotu a początkiem innego lotu
                Duration gapBetweenFlights = Duration.between(
                        flight.getClearTime(),
                        otherFlight.getReportTime());


                // Jeśli przerwa między lotami jest mniejsza niż minimalna, zwróć false
                if (gapBetweenFlights.compareTo(minLongFlightGap) < 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean hasSufficientShortFlightGap(Flight flight) {
        Duration minShortFlightGap = Duration.ofHours(12).plusMinutes(30);

        List<Flight> availableFlights = getRequests().stream()
                .map(FlightRequest::getFlight)
                .toList();

        for (Flight otherFlight : availableFlights) {
            // Pomijaj ten sam lot
            if (flight.equals(otherFlight)) {
                continue;
            }

            // Oblicz różnicę czasu między końcem obecnego lotu a początkiem innego lotu
            Duration gapBetweenFlights = Duration.between(
                    flight.getClearTime(),
                    otherFlight.getReportTime());

            // Jeśli przerwa między lotami jest mniejsza niż minimalna, zwróć false
            if (gapBetweenFlights.compareTo(minShortFlightGap) < 0) {
                return false;
            }
        }

        return true;
    }

    private boolean isFirstFlightAfterDayOff(Flight flight, Period currentPeriod, List<EventRequest> nextEventRequest) {
        LocalDate dayBeforeFlight = flight.getReportTime().toLocalDate().minusDays(1);
        for (EventRequest eventRequest : nextEventRequest) {
            LocalDate eventStart = eventRequest.getStartTime().toLocalDate();
            LocalDate eventEnd = eventRequest.getEndTime().toLocalDate();
            if (!dayBeforeFlight.isBefore(eventStart) && !dayBeforeFlight.isAfter(eventEnd)) {
                return true;
            }
        }
        return false;
    }
}
