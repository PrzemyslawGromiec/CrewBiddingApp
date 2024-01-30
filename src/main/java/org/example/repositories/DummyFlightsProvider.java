package org.example.repositories;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.services.FlightsProvider;

import java.time.*;
import java.util.*;

public class DummyFlightsProvider implements FlightsProvider {

    private LocalDate today;

    @Override
    public List<Flight> provideFlights() {
        today = LocalDate.now();
        List<Flight> flightList = new ArrayList<>();
        flightList.addAll(generateCustomRecurringFlights("BA456", "MAD", AircraftType.A320,
                LocalTime.of(5, 5), Duration.ofHours(7).plusMinutes(25)));
        flightList.addAll(generateCustomRecurringFlights("BA710", "ZRH", AircraftType.A319,
                LocalTime.of(5, 15), Duration.ofHours(6).plusMinutes(25)));
        flightList.addAll(generateCustomRecurringFlights("BA328", "CDG", AircraftType.A320,
                LocalTime.of(5, 20), Duration.ofHours(7).plusMinutes(25)));
        flightList.addAll(generateCustomRecurringFlights("BA210", "WAW", AircraftType.A320,
                LocalTime.of(10, 30), Duration.ofHours(10)));
        flightList.addAll(generateCustomRecurringFlights("BA150", "FRA", AircraftType.A320,
                LocalTime.of(9, 25), Duration.ofHours(8)));
        flightList.addAll(generateCustomRecurringFlights("BA243", "FCO", AircraftType.A320,
                LocalTime.of(5, 25), Duration.ofHours(11)));
        flightList.addAll(generateCustomRecurringFlightsForSelectedDays("BA190", "NCE", AircraftType.A320,
                LocalTime.of(6, 25), Duration.ofHours(7).plusMinutes(25), EnumSet.complementOf(EnumSet.of(DayOfWeek.FRIDAY))));
        flightList.addAll(generateCustomRecurringFlights("BA582", "VCE", AircraftType.A320,
                LocalTime.of(12, 20), Duration.ofHours(7).plusMinutes(25)));
        flightList.addAll(generateCustomRecurringFlightsForSelectedDays("BA05", "HND", AircraftType.B787,
                LocalTime.of(10, 10), Duration.ofHours(70), EnumSet.complementOf(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))));
        flightList.addAll(generateCustomRecurringFlights("BA15", "SIN", AircraftType.B787, LocalTime.of(10, 5),
                Duration.ofHours(46)));
        flightList.addAll(generateCustomRecurringFlights("BA220", "BDA", AircraftType.B777,
                LocalTime.of(13, 30), Duration.ofHours(40)));
        flightList.addAll(generateCustomRecurringFlights("BA224", "YYZ", AircraftType.B777,
                LocalTime.of(11, 30), Duration.ofHours(42)));
        return Collections.unmodifiableList(flightList);
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

     public void setToday(LocalDate today) {
        this.today = today;
    }
}
