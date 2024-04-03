package bidding.crew.repository.generator;

import bidding.crew.entity.AircraftType;

import java.time.*;
import java.util.*;

class DummyFlightsTemplateProvider implements FlightsTemplateProvider {

    @Override
    public List<FlightTemplate> provideFlights() {

        List<FlightTemplate> flightList = new ArrayList<>();
        flightList.add(new FlightTemplate("BA320", "MXP",LocalTime.of(5,10)));
        flightList.add(new FlightTemplate("BA100","CDG", LocalTime.of(5,25),LocalTime.of(15,10),1,
                List.of(DayOfWeek.MONDAY,DayOfWeek.WEDNESDAY), AircraftType.A319));
        return Collections.unmodifiableList(flightList);
    }
}
