package bidding.crew.repository.generator;

import bidding.crew.entity.AircraftType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringMapperFlightsTemplateProviderTest {

    @Test
    void parseLineToFlight() {
        StringMapperFlightsTemplateProvider mapperFlightsProvider = new StringMapperFlightsTemplateProvider(null);
        FlightTemplate flight = mapperFlightsProvider.parseLineToFlight("✓ 04.30 REPORT BA0337 MRS").get();
        assertEquals("BA0337", flight.getFlightNumber());
        assertEquals("MRS", flight.getAirportCode());
        assertEquals(4,flight.getReportTime().getHour());
        assertEquals(30,flight.getReportTime().getMinute());
       // assertEquals(flight.getClearTime().toLocalDate(), flight.getReportTime());
        Assertions.assertEquals(AircraftType.A320, flight.getAircraftType());
        //dni tygodnia
       //Assertions.assertFalse(flight.isFavourite());

    }
}

//✓ 04.30 REPORT BA0337 MRS