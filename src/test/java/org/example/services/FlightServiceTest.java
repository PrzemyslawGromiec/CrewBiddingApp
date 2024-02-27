package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.entities.Period;
import org.example.repositories.generator.DummyFlightsProvider;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceTest {

    //test parametryzowany

    @Test
    public void flightGenerator(){
        DummyFlightsProvider flightsProvider = new DummyFlightsProvider();

        FlightService flightService = new FlightService(flightsProvider, preferencesService);
        flightsProvider.setToday(LocalDate.of(2023,11,28));

        List<Flight> wawFlight = flightsProvider.generateCustomRecurringFlights("BA210", "WAW",
                AircraftType.A320, LocalTime.of(12,30), Duration.ofHours(10));

        int nextMonth = LocalDate.now().plusMonths(1).getMonth().maxLength();

        LocalDateTime lastClearTime = LocalDateTime.of(2023,12,31,22,30);

        assertEquals(nextMonth,wawFlight.size());

        assertEquals(lastClearTime,wawFlight.get(wawFlight.size()-1).getClearTime());

    }

    @Test
    public void fillingPeriodWithFlights() {
        Period period = new Period(LocalDateTime.of(2024,1,12,5,0),
                LocalDateTime.of(2024,1,13,23,0));
        FlightService flightService = new FlightService(new DummyFlightsProvider(), preferencesService);

        List<Flight> flights = flightService.generateFlightsForPeriod(period);

        assertEquals(7, flights.size());

    }
}
