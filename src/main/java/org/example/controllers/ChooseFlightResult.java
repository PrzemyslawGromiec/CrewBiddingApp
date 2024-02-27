package org.example.controllers;

import org.example.entities.Flight;

import java.util.Optional;

public class ChooseFlightResult {
        enum Status{
                FLIGHT_CHOSEN, PERIOD_SKIPPED, SHOW_ALL_DURATIONS
        }

        private Status status;
        private Optional<Flight> flight;

        public ChooseFlightResult(Status status, Optional<Flight> flight) {
                this.status = status;
                this.flight = flight;
        }

        public Status getStatus() {
                return status;
        }

        public Optional<Flight> getFlight() {
                return flight;
        }
}
