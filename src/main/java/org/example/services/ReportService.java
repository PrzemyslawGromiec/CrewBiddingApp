package org.example.services;

import org.example.entities.EventRequest;
import org.example.entities.FlightRequest;
import org.example.entities.Report;
import org.example.entities.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    /*
     * event requests powinny miec wieksze priority
     * flight requests rozpatrywane potem
     * max points: 200, min points: 10, odstep 10 points pomiedzy requestami
     *
     * */
    private List<EventRequest> eventRequests;
    private List<FlightRequest> flightRequests;
    private RequestService requestService;


    public ReportService(RequestService requestService) {
        this.requestService = requestService;
        this.eventRequests = requestService.getEventRequests();
        this.flightRequests = requestService.getFlightRequests();
    }

    public void calculatePointsForRequest() {
        //policzyc punkty dla EventRequest i dla FlightRequest
        //EventRequest powinien miec wieksze priority
        int points = 200;
      /*  List<EventRequest> eventRequests = requestService.getEventRequests();
        List<FlightRequest> flightRequests = requestService.getFlightRequests();*/
        List<Request> allRequestsSortedByStars = requestSortedByStars();
        allRequestsSortedByStars.forEach(System.out::println);


    }

    public Report finalizedReport(List<FlightRequest> flightRequests, List<EventRequest> eventRequests) {

        return null;
    }

    /*    public List<Request> requestSortedByStars() {
            return allRequests.stream()
                    .sorted(Comparator.comparing(Request::getNumberOfStars)
                            .reversed())
                    .collect(Collectors.toList());
        }*/
    public List<Request> requestSortedByStars() {

        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(eventRequests);
        allRequests.addAll(flightRequests);
        return allRequests.stream()
                .sorted(Comparator.comparing(Request::getNumberOfStars)
                        .reversed()).toList();
    }


}
