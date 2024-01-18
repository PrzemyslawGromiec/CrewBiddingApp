package org.example.services;

import org.example.entities.EventRequest;
import org.example.entities.FlightRequest;
import org.example.entities.Report;
import org.example.entities.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    public Report finalizedReport(List<FlightRequest> flightRequests, List<EventRequest> eventRequests) {
        List<Request> requests = requestSortedByStars(flightRequests, eventRequests);
        calculateStarsForPoints(requests);
        List<Request> requestSortedByDates = sortByDates(requests);
        return new Report(LocalDate.now(), requestSortedByDates);
    }

    private List<Request> requestSortedByStars(List<FlightRequest> flightRequests, List<EventRequest> eventRequests) {
        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(flightRequests);
        allRequests.addAll(eventRequests);
        return allRequests.stream()
                .sorted(Comparator.comparing(Request::getNumberOfStars)
                        .reversed()).toList();
    }

    private void calculateStarsForPoints(List<Request> finalRequests) {
        int maxPoints = 200;
        for (Request finalRequest : finalRequests) {
            finalRequest.setPoints(maxPoints);
            maxPoints -= 10;
        }
    }

    private List<Request> sortByDates(List<Request> requests) {
        return requests.stream()
                .sorted(Comparator.comparing(Request::getStartTime))
                .collect(Collectors.toList());

    }


}
