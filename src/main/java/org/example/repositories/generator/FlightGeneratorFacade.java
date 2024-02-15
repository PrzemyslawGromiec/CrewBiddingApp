package org.example.repositories.generator;

import org.example.entities.Flight;

import java.io.FileNotFoundException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FlightGeneratorFacade {

    private FlightsTemplateProvider flightsTemplateProvider;

    //todo: czy to nie powinien byc kolejny miesiac?
    private LocalDate today = LocalDate.now();

    private FlightGeneratorFacade(FlightsTemplateProvider flightsTemplateProvider) {
        this.flightsTemplateProvider = flightsTemplateProvider;

    }

    public List<Flight> generateFlights() {
        List<FlightTemplate> flightTemplates = flightsTemplateProvider.provideFlights();
        List<Flight> flights = new ArrayList<>();
        for (FlightTemplate flightTemplate : flightTemplates) {
            flights.addAll(generateCustomRecurringFlights(flightTemplate));
        }
        return flights;

        //throw new UnsupportedOperationException();//todo
    }

    private List<Flight> generateCustomRecurringFlights(FlightTemplate flightTemplate) {
        List<Flight> flights = new ArrayList<>();
        for (int day = 1; day <= today.getMonth().maxLength(); day++) {
            if (!flightTemplate.flightsOn(today.withDayOfMonth(day).getDayOfWeek())) {
                continue;
            }

            LocalDateTime report = LocalDateTime.of(today.withDayOfMonth(day), flightTemplate.getReportTime());
            LocalDateTime clear = LocalDateTime.of(today.withDayOfMonth(day).plusDays(flightTemplate.getDurationDays()), flightTemplate.getClearTime());

            flights.add(new Flight(flightTemplate.getFlightNumber(), flightTemplate.getAirportCode(), report, clear, flightTemplate.getAircraftType()));

        }
        return flights;
    }

    public static class Factory {
        public static FlightGeneratorFacade createFlightFacade(Source source) throws FileNotFoundException {
            if (source == Source.FILE) {
                TextFileLoader textFileLoader = new TextFileLoader("Flights.txt");
                return new FlightGeneratorFacade(new StringMapperFlightsTemplateProvider(textFileLoader.readFile()));
            } else if (source == Source.DUMMY) {
                return new FlightGeneratorFacade(new DummyFlightsTemplateProvider());
            }
            throw new IllegalStateException("Source not implemented.");
        }
    }
}


//wzorzec fasada