package org.example.repositories.generator;

import org.example.entities.AircraftType;

import java.time.*;
import java.util.*;

class DummyFlightsTemplateProvider implements FlightsTemplateProvider {

    private LocalDate today;

    @Override
    public List<FlightTemplate> provideFlights() {

        List<FlightTemplate> flightList = new ArrayList<>();
        flightList.add(new FlightTemplate("BA320", "MXP",LocalTime.of(5,10)));
        flightList.add(new FlightTemplate("BA100","CDG", LocalTime.of(5,25),LocalTime.of(15,10),1,
                List.of(DayOfWeek.MONDAY,DayOfWeek.WEDNESDAY),AircraftType.A319));
/*        flightList.add(new FlightTemplate("BA456", "MAD",
                LocalDateTime.of(5, 5), Duration.ofHours(7).plusMinutes(25),AircraftType.A320));*/
        /*flightList.addAll(generateCustomRecurringFlights("BA710", "ZRH", AircraftType.A319,
                LocalTime.of(5, 15), Duration.ofHours(6).plusMinutes(25)));
        flightList.addAll(generateCustomRecurringFlights("BA328", "CDG", AircraftType.A320,
                LocalTime.of(5, 20), Duration.ofHours(7).plusMinutes(25)));
        flightList.addAll(generateCustomRecurringFlights("BA210", "WAW", AircraftType.A320,
                LocalTime.of(10, 30), Duration.ofHours(10)));
        flightList.addAll(generateCustomRecurringFlights("BA150", "FRA", AircraftType.A320,
                LocalTime.of(9, 25), Duration.ofHours(8)));
        flightList.addAll(generateCustomRecurringFlights("BA243", "FCO", AircraftType.A320,
                LocalTime.of(5, 25), Duration.ofHours(11)));
        *//*flightList.addAll(generateCustomRecurringFlightsForSelectedDays("BA190", "NCE", AircraftType.A320,
                LocalTime.of(6, 25), Duration.ofHours(7).plusMinutes(25), EnumSet.complementOf(EnumSet.of(DayOfWeek.FRIDAY))));*//*
        flightList.addAll(generateCustomRecurringFlights("BA582", "VCE", AircraftType.A320,
                LocalTime.of(12, 20), Duration.ofHours(7).plusMinutes(25)));
        *//*flightList.addAll(generateCustomRecurringFlightsForSelectedDays("BA05", "HND", AircraftType.B787,
                LocalTime.of(10, 10), Duration.ofHours(70), EnumSet.complementOf(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))));*//*
        flightList.addAll(generateCustomRecurringFlights("BA15", "SIN", AircraftType.B787, LocalTime.of(10, 5),
                Duration.ofHours(46)));
        flightList.addAll(generateCustomRecurringFlights("BA220", "BDA", AircraftType.B777,
                LocalTime.of(13, 30), Duration.ofHours(40)));
        flightList.addAll(generateCustomRecurringFlights("BA224", "YYZ", AircraftType.B777,
                LocalTime.of(11, 30), Duration.ofHours(42)));*/
        return Collections.unmodifiableList(flightList);
    }



     public void setToday(LocalDate today) {
        this.today = today;
    }
}
