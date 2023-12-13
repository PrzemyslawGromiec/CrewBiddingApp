package org.example.controllers;

import org.example.entities.FlightRequest;
import org.example.entities.Period;
import org.example.services.FlightRequestFactory;
import org.example.services.FlightService;
import org.example.entities.Flight;

import java.util.List;
import java.util.Scanner;

public class FlightController {
    private FlightPeriodController periodController;

    public FlightController(FlightService flightService) {
        periodController = new FlightPeriodController(flightService);
    }

    public List<FlightRequest> chooseFlightsForPeriods(List<Period> generatedPeriods) {
        FlightRequestFactory factory = new FlightRequestFactory();
        for (Period generatedPeriod : generatedPeriods) {
            System.out.println("Current period:" + generatedPeriod);
            periodController.chooseFlight(generatedPeriod, factory);
            generatedPeriod.shortenPeriod(null);
        }

        List<FlightRequest> requests = factory.getRequests();
        return requests;
    }

    //analiza aktualnego działania
    //refactor
    //zasada poziomów abstrakcji
    //plan na korzystanie z periodów w scenariuszu w który w trakcie powstają nowe (bo są dzielone na mniejsze)
    //TDD
    //implementacja



}
