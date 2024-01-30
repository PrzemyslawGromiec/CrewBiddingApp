package org.example.repositories;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class StringMapperFlightsProviderTest {

    @Test
    void parseLineToFlight() {
        StringMapperFlightsProvider mapperFlightsProvider = new StringMapperFlightsProvider(null);
        Flight flight = mapperFlightsProvider.parseLineToFlight("✓ 04.30 REPORT BA0337 MRS");
        Assertions.assertEquals("BA0337", flight.getFlightNumber());
        Assertions.assertEquals("MRS", flight.getAirportCode());
        Assertions.assertEquals(4,flight.getReportTime().getHour());
        Assertions.assertEquals(30,flight.getReportTime().getMinute());
        assertEquals(flight.getClearTime().toLocalDate(), flight.getReportTime().toLocalDate());
        Assertions.assertEquals(AircraftType.A320, flight.getAircraftType());
        Assertions.assertFalse(flight.isFavourite());

    }
}

//✓ 04.30 REPORT BA0337 MRS