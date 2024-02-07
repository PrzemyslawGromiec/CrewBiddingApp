package org.example.repositories.generator;

import org.example.entities.Flight;

import java.io.FileNotFoundException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FlightGeneratorFacade {

    private FlightsTemplateProvider flightsTemplateProvider;
    private LocalDate today = LocalDate.now();

    private FlightGeneratorFacade(FlightsTemplateProvider flightsTemplateProvider) {
        this.flightsTemplateProvider = flightsTemplateProvider;

    }

    public List<Flight> generateFlights() {
        List<FlightTemplate> flights = flightsTemplateProvider.provideFlights();

        throw new UnsupportedOperationException();//todo
    }

    private List<Flight> generateCustomRecurringFlights(FlightTemplate flightTemplate) {
        List<Flight> flights = new ArrayList<>();
        for (int day = 1; day <= today.getMonth().maxLength(); day++) {

            LocalDateTime report = LocalDateTime.of(today.withDayOfMonth(day), flightTemplate.getReportTime());
            LocalDateTime clear = LocalDateTime.of(today.withDayOfMonth(day).plusDays(flightTemplate.getDurationDays()), flightTemplate.getClearTime());

            flights.add(new Flight(flightTemplate.getFlightNumber(),flightTemplate.getAirportCode(),report,clear,flightTemplate.getAircraftType()));

        }
        return flights;
    }

    private List<Flight> generateCustomRecurringFlightsForSelectedDays(Flight flight, Set<DayOfWeek> operationDays) {
        List<Flight> flightsForSelectedDays = new ArrayList<>();
       /* LocalDate date = today.plusMonths(1).withDayOfMonth(1);

        while (date.getMonth() == today.plusMonths(1).getMonth()) {
            if (operationDays.contains(date.getDayOfWeek())) {
                LocalDateTime takeoffDateTime = date.atTime(flight.getReportTime());
                LocalDateTime landingDateTime = takeoffDateTime.plus(flight.getClearTime());
                flightsForSelectedDays.add(new Flight(flight.getFlightNumber(), flight.getAirportCode(), takeoffDateTime, landingDateTime, flight.getAircraftType()));
            }
            date = date.plusDays(1);
        }*/
        return flightsForSelectedDays;
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