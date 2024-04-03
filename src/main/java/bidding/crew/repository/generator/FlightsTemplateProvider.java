package bidding.crew.repository.generator;

import java.util.List;

interface FlightsTemplateProvider {
    List<FlightTemplate> provideFlights();
}
