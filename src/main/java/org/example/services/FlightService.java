package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.entities.Period;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();
    private LocalDate today;

    public FlightService() {
        this.today = LocalDate.now();
        flights.addAll(generateCustomRecurringFlights("BA210", "WAW", AircraftType.A320,
                LocalTime.of(10, 30), Duration.ofHours(10)));
        flights.addAll(generateCustomRecurringFlights("BA150", "FRA", AircraftType.A320,
                LocalTime.of(9, 25), Duration.ofHours(8)));
        flights.addAll(generateCustomRecurringFlights("BA243", "FCO", AircraftType.A320,
                LocalTime.of(5, 25), Duration.ofHours(11)));
        flights.addAll(generateCustomRecurringFlightsForSelectedDays("BA190", "NCE", AircraftType.A320,
                LocalTime.of(6, 25), Duration.ofHours(7).plusMinutes(25), EnumSet.complementOf(EnumSet.of(DayOfWeek.FRIDAY))));
        flights.addAll(generateCustomRecurringFlightsForSelectedDays("BA05", "HND", AircraftType.B787,
                LocalTime.of(10, 10), Duration.ofHours(70), EnumSet.complementOf(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))));

    }

    public List<Flight> generateCustomRecurringFlights(String flightNumber, String airportCode, AircraftType type, LocalTime takeoffTime, Duration flightLength) {
        List<Flight> flights = new ArrayList<>();
        LocalDateTime takeoffDate = today.plusMonths(1).withDayOfMonth(1).atTime(takeoffTime);
        LocalDateTime landingTime = takeoffDate.plus(flightLength);

        while (takeoffDate.getMonth() == today.plusMonths(1).getMonth()) {

            flights.add(new Flight(flightNumber, airportCode, takeoffDate, landingTime, type));
            takeoffDate = takeoffDate.plusDays(1);
            landingTime = landingTime.plusDays(1);
        }
        return flights;
    }

    public List<Flight> generateCustomRecurringFlightsForSelectedDays(String flightNumber, String airportCode,
                                                                      AircraftType type, LocalTime takeoffTime, Duration flightLength, Set<DayOfWeek> operationDays) {
        List<Flight> flightsForSelectedDays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate date = today.plusMonths(1).withDayOfMonth(1);

        while (date.getMonth() == today.plusMonths(1).getMonth()) {
            if (operationDays.contains(date.getDayOfWeek())) {
                LocalDateTime takeoffDateTime = date.atTime(takeoffTime);
                LocalDateTime landingDateTime = takeoffDateTime.plus(flightLength);
                flightsForSelectedDays.add(new Flight(flightNumber, airportCode, takeoffDateTime, landingDateTime, type));
            }
            date = date.plusDays(1);
        }
        return flightsForSelectedDays;
    }


    public List<Flight> fillPeriodWithFlights(Period period) {
        return flights.stream()
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .collect(Collectors.toList());
    }


    public List<Flight> getFlights() {
        return flights;
    }

    void setToday(LocalDate today) {
        this.today = today;
    }
}
