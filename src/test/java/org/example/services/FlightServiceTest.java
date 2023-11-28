package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.entities.Period;
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

        FlightService flightService = new FlightService();
        flightService.setToday(LocalDate.of(2023,11,28));

        List<Flight> wawFlight = flightService.generateCustomRecurringFlights("BA210", "WAW",
                AircraftType.A320, LocalTime.of(12,30), Duration.ofHours(10));

        int nextMonth = LocalDate.now().plusMonths(1).getMonth().maxLength();

        LocalDateTime lastClearTime = LocalDateTime.of(2023,12,31,22,30);

        assertEquals(nextMonth,wawFlight.size());

        assertEquals(lastClearTime,wawFlight.get(wawFlight.size()-1).getClearTime());

    }

    @Test
    public void fillingPeriodWithFlights() {
        Period period = new Period(LocalDateTime.of(2023,12,12,5,0), LocalDateTime.of(2023,12,13,23,0));
        FlightService flightService = new FlightService();

        flightService.fillPeriodWithFlights(period);
        assertEquals(6, period.getFlights().size());

    }
}
