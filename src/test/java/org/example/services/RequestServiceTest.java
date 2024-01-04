package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.entities.Period;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {
    private RequestService requestService = new RequestService();


    @Test
    public void testFilterBuffer(){
        //flights
        Flight flight1 = new Flight("BA456", "MAD", LocalDateTime.of(2024,1,1,6,30),
                LocalDateTime.of(2024,1,1,6,30),AircraftType.A320);
        Flight flight2 = createFlight(4,5,7);
        Flight flight3 = createFlight(5,5,6);
        //flight4 nie powinien wejsc do mojego period2, ale flight 5 juz moze
        Flight flight4 = createFlight(8,5,5);
        Flight flight5 = createFlight(8,10,5);
        Flight flight6 = createFlight(9,5,6);
        Flight flight7 = createFlight(9,7,4);
        Flight flight8 = createFlight(10, 6,6);

        Period period1 = new Period(LocalDateTime.of(2024,1,3,10,10),
                LocalDateTime.of(2024,1,5,10,15));
        Period period2 = new Period(LocalDateTime.of(2024,1,8,10,10),
                LocalDateTime.of(2024,1,10,10,15));
        List<Flight> flightsForCurrentPeriod2 = Arrays.asList(flight4,flight5,flight6,flight7,flight8);

        requestService.buildRequest(flight4,2);
        requestService.buildRequest(flight5,2);
        requestService.buildRequest(flight7,3);
        requestService.buildRequest(flight8,2);

        List<Flight> filteredFlights = requestService.filterBuffer(flightsForCurrentPeriod2,period2);
        System.out.println(filteredFlights.size());
        System.out.println(filteredFlights);
        //Assertions.assertEquals(4,filteredFlights.size());


    }

    private Flight createFlight(int startDayOfMonth,int startingHour,  int hoursFlight){
        return new Flight("","", LocalDateTime.of(2024, 1,startDayOfMonth,startingHour,0),
                LocalDateTime.of(2024, 1,startDayOfMonth,startingHour,0).plusHours(hoursFlight),AircraftType.A320 );
    }

}