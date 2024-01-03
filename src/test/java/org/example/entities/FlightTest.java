package org.example.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class FlightTest {

    @Test
    public void compareFlightsWithBuffers() {
        Flight flight = createFlight(1,1,24); // 4 , 1
        Flight flight2 = createFlight(3,1, 24);
        Flight flight3 = createFlight(4,1, 24);
        Flight flight4 = createFlight(4,2, 24);

        Assertions.assertFalse(flight.isValidFlightInPeriod(flight2));
        Assertions.assertFalse(flight.isValidFlightInPeriod(flight3));
        Assertions.assertTrue(flight.isValidFlightInPeriod(flight4));

        Assertions.assertFalse(flight2.isValidFlightInPeriod(flight));
        Assertions.assertFalse(flight3.isValidFlightInPeriod(flight));
        Assertions.assertTrue(flight4.isValidFlightInPeriod(flight));

        Assertions.assertFalse(flight2.isValidFlightInPeriod(flight3));
        Assertions.assertFalse(flight3.isValidFlightInPeriod(flight2));

        Assertions.assertFalse(flight2.isValidFlightInPeriod(flight4));
        Assertions.assertFalse(flight4.isValidFlightInPeriod(flight2));


    }

    private Flight createFlight(int startDayOfMonth,int startingHour,  int hoursFlight){
        return new Flight("","", LocalDateTime.of(2024, 1,startDayOfMonth,startingHour,0),
                LocalDateTime.of(2024, 1,startDayOfMonth,startingHour,0).plusHours(hoursFlight),AircraftType.A320 );
    }


}