package org.example.repositories.generator;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringMapperFlightsTemplateProviderTest {

    @Test
    void parseLineToFlight() {
        StringMapperFlightsTemplateProvider mapperFlightsProvider = new StringMapperFlightsTemplateProvider(null);
        FlightTemplate flight = mapperFlightsProvider.parseLineToFlight("✓ 04.30 REPORT BA0337 MRS").get();
        Assertions.assertEquals("BA0337", flight.getFlightNumber());
        Assertions.assertEquals("MRS", flight.getAirportCode());
        Assertions.assertEquals(4,flight.getReportTime().getHour());
        Assertions.assertEquals(30,flight.getReportTime().getMinute());
       // assertEquals(flight.getClearTime().toLocalDate(), flight.getReportTime());
        Assertions.assertEquals(AircraftType.A320, flight.getAircraftType());
        //dni tygodnia
       //Assertions.assertFalse(flight.isFavourite());

    }
}

//✓ 04.30 REPORT BA0337 MRS