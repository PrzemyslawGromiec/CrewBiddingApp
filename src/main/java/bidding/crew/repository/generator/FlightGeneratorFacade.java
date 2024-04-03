package bidding.crew.repository.generator;

import bidding.crew.entity.Flight;
import bidding.crew.general.Time;

import java.io.FileNotFoundException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class FlightGeneratorFacade {
    private FlightsTemplateProvider flightsTemplateProvider;
    private Time time;

    private FlightGeneratorFacade(FlightsTemplateProvider flightsTemplateProvider, Time time) {
        this.flightsTemplateProvider = flightsTemplateProvider;
        this.time = time;
    }

    public List<Flight> generateFlights() {
        List<FlightTemplate> flightTemplates = flightsTemplateProvider.provideFlights();
        List<Flight> flights = new ArrayList<>();
        for (FlightTemplate flightTemplate : flightTemplates) {
            flights.addAll(generateCustomRecurringFlights(flightTemplate));
        }
        return flights;
    }

    private List<Flight> generateCustomRecurringFlights(FlightTemplate flightTemplate) {
        List<Flight> flights = new ArrayList<>();
        LocalDate nextMonth = time.nextMonthLocalDate();
        for (int day = 1; day <= nextMonth.getMonth().maxLength(); day++) {
            if (!flightTemplate.flightsOn(nextMonth.withDayOfMonth(day).getDayOfWeek())) {
                continue;
            }
            LocalDateTime report = LocalDateTime.of(nextMonth.withDayOfMonth(day), flightTemplate.getReportTime());
            LocalDateTime clear = LocalDateTime.of(nextMonth.withDayOfMonth(day).plusDays(flightTemplate.getDurationDays()), flightTemplate.getClearTime());
            flights.add(new Flight(flightTemplate.getFlightNumber(), flightTemplate.getAirportCode(), report, clear, flightTemplate.getAircraftType()));
        }
        return flights;
    }

    public static class Factory {
        public static FlightGeneratorFacade createFlightFacade(Source source) throws FileNotFoundException {
            if (source == Source.FILE) {
                TextFileLoader textFileLoader = new TextFileLoader("Flights.txt"); //todo: wyeksponowac
                return new FlightGeneratorFacade(new StringMapperFlightsTemplateProvider(textFileLoader.readFile()),Time.getTime());
            } else if (source == Source.DUMMY) {
                return new FlightGeneratorFacade(new DummyFlightsTemplateProvider(),Time.getTime());
            }
            throw new IllegalStateException("Source not implemented.");
        }
    }
}