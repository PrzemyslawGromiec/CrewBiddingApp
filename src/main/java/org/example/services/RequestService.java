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

    public List<PointsReport> calculatePointsForRequest() {
        //policzyc punkty dla EventRequest i dla FlightRequest
        //EventRequest powinien miec wieksze priority
        int points = 200;
        //TODO: kazdy EventRequest to lista eventow, problem z priority
        List<EventRequest> eventRequests = eventsSortedByPoints();
        List<FlightRequest> flightRequests = flightsSortedByPoints();
        List<PointsReport> pointsReports = new ArrayList<>();

        for (EventRequest eventRequest : eventRequests) {
            int calculatedPoints = points - 10;
            PointsReport report = new PointsReport(eventRequest.getStartTime().toString(), calculatedPoints);
            pointsReports.add(report);
            points = calculatedPoints;
            System.out.println(report.getRequestType() + " : " + report.getPoints());
        }

        points -= 10;

        for (FlightRequest request : flightRequests) {
            int calculatedPoints = points - 10;
            PointsReport report = new PointsReport(request.getFlight().toString(), calculatedPoints);
            pointsReports.add(report);
            points = calculatedPoints;
            System.out.println(report.getRequestType() + " : " + report.getPoints());
        }

        return pointsReports;
    }

    public List<FlightRequest> flightsSortedByPoints() {
        return getFlightRequests().stream()
                .sorted(Comparator.comparing(FlightRequest::getNumberOfStars)
                        .reversed())
                .collect(Collectors.toList());
    }

    public List<EventRequest> eventsSortedByPoints() {
        return getEventRequests().stream()
                .sorted(Comparator.comparing(EventRequest::getNumberOfStars)
                        .reversed())
                .collect(Collectors.toList());
    }

}
