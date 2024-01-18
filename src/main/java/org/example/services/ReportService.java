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
    private RequestService requestService;


    public ReportService(RequestService requestService) {
        this.requestService = requestService;
    }

   /* public void calculatePointsForRequest() {
        //policzyc punkty dla EventRequest i dla FlightRequest
        //EventRequest powinien miec wieksze priority
        int points = 200;
      *//*  List<EventRequest> eventRequests = requestService.getEventRequests();
        List<FlightRequest> flightRequests = requestService.getFlightRequests();*//*
        List<Request> allRequestsSortedByStars = requestSortedByStars();
        System.out.println("linijka przed sortowaniem");
        allRequestsSortedByStars.forEach(System.out::println);
        System.out.println("cos printuje");


    }*/

    public Report finalizedReport(List<FlightRequest> flightRequests, List<EventRequest> eventRequests) {
        List<Request> requests = requestSortedByStars(flightRequests, eventRequests);
        for (Request request : requests) {
            System.out.println(request);
        }
        return null;
    }

    public List<Request> requestSortedByStars(List<FlightRequest> flightRequests, List<EventRequest> eventRequests) {
        List<Request> allRequests = new ArrayList<>();
        allRequests.addAll(flightRequests);
        allRequests.addAll(eventRequests);
        return allRequests.stream()
                .sorted(Comparator.comparing(Request::getNumberOfStars)
                        .reversed()).toList();
    }


}
