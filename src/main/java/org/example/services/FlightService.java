package org.example.services;

import org.example.entities.AircraftType;
import org.example.entities.Flight;
import org.example.entities.Period;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();
    private LocalDate today;

    public FlightService() {
        this.today = LocalDate.now();
        flights.addAll(generateCustomRecurringFlights("BA210", "WAW", AircraftType.A320,
                LocalTime.of(10, 30), Duration.ofHours(10)));
        flights.addAll(generateCustomRecurringFlights("BA150", "FRA", AircraftType.A320,
                LocalTime.of(9, 25), Duration.ofHours(8)));
        flights.addAll(generateCustomRecurringFlights("BA243", "FCO", AircraftType.A320,
                LocalTime.of(5, 25), Duration.ofHours(11)));
        flights.addAll(generateCustomRecurringFlightsForSelectedDays("BA190", "NCE", AircraftType.A320,
                LocalTime.of(6, 25), Duration.ofHours(7).plusMinutes(25), EnumSet.complementOf(EnumSet.of(DayOfWeek.FRIDAY))));
        flights.addAll(generateCustomRecurringFlightsForSelectedDays("BA05", "HND", AircraftType.B787,
                LocalTime.of(10, 10), Duration.ofHours(70), EnumSet.complementOf(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))));
        flights.addAll(generateCustomRecurringFlights("BA15", "SIN", AircraftType.B787, LocalTime.of(10, 5),
                Duration.ofHours(24)));
        flights.addAll(generateCustomRecurringFlights("BA220", "BDA", AircraftType.B777,
                LocalTime.of(13, 30), Duration.ofHours(26)));
        flights.addAll(generateCustomRecurringFlights("BA224", "YYZ", AircraftType.B777,
                LocalTime.of(11, 30), Duration.ofHours(28)));

    }

    public List<Flight> generateCustomRecurringFlights(String flightNumber, String airportCode, AircraftType type, LocalTime takeoffTime, Duration flightLength) {
        List<Flight> flights = new ArrayList<>();
        LocalDateTime takeoffDate = today.plusMonths(1).withDayOfMonth(1).atTime(takeoffTime);
        LocalDateTime landingTime = takeoffDate.plus(flightLength);

        while (takeoffDate.getMonth() == today.plusMonths(1).getMonth()) {

            flights.add(new Flight(flightNumber, airportCode, takeoffDate, landingTime, type));
            takeoffDate = takeoffDate.plusDays(1);
            landingTime = landingTime.plusDays(1);
        }
        return flights;
    }

    public List<Flight> generateCustomRecurringFlightsForSelectedDays(String flightNumber, String airportCode,
                                                                      AircraftType type, LocalTime takeoffTime, Duration flightLength, Set<DayOfWeek> operationDays) {
        List<Flight> flightsForSelectedDays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate date = today.plusMonths(1).withDayOfMonth(1);

        while (date.getMonth() == today.plusMonths(1).getMonth()) {
            if (operationDays.contains(date.getDayOfWeek())) {
                LocalDateTime takeoffDateTime = date.atTime(takeoffTime);
                LocalDateTime landingDateTime = takeoffDateTime.plus(flightLength);
                flightsForSelectedDays.add(new Flight(flightNumber, airportCode, takeoffDateTime, landingDateTime, type));
            }
            date = date.plusDays(1);
        }
        return flightsForSelectedDays;
    }


    public List<Flight> generateFlightsForPeriod(Period period) {
        return flights.stream()
                .filter(flight -> !flight.getReportTime().isBefore(period.getStartTime())
                        && !flight.getClearTime().isAfter(period.getEndTime()))
                .sorted((flight1, flight2) ->flight2.getFlightDuration().compareTo(flight1.getFlightDuration()))
                .collect(Collectors.toList());
    }

    public List<Flight> fillPeriodWithPreferredFlights(Period period) {

        Scanner scanner = new Scanner(System.in);
        List<Flight> availableFlights = generateFlightsForPeriod(period);
        List<Flight> selectedFlights = new ArrayList<>();

        System.out.println("What combination of flights are you interested in:");
        System.out.println("1.Short-haul, 2.Long-haul, 3.Mix of both");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Short-haul flights for you:");
                selectedFlights = availableFlights.stream()
                        .filter(flight -> flight.getFlightDuration().compareTo(Duration.ofHours(14)) < 0)
                        .collect(Collectors.toList());
                break;
            case 2:
                System.out.println("Long-haul flights for you:");
                selectedFlights = availableFlights.stream()
                        .filter(flight -> flight.getFlightDuration().compareTo(Duration.ofHours(14)) >= 0)
                        .collect(Collectors.toList());

                break;
            case 3:
                System.out.println("Mix of both - short-haul and long-haul:");
                chooseCombinationOfFlights(fillPeriodWithMixOfShortAndLong(period));

                /*
                 * mam dlugos okresu np 3 dni 10 godzin
                 * licze jego dlugosc okresu
                 * ma to byc mix short i long
                 * sprawdzam dlugosc wszystkich long i zostawiam te ktorych dlugosc plus 12.5h jest krotsze niz period
                 * przegladam short i sprawdzam czy dlugosc short + powyzsza wartosc miesci sie (jest mniejsza badz rowna) od period
                 * jesli tak, to powinienem stworzyc kombinacje short i long z lotow, ktore spelniaja powyzsze warunki
                 *
                 * szukam wszystkich long haul, ktore maja dlugosc mniejsza badz rowna od okresu
                 * jesli istnieje taki, to dodaje go na koncu mojego okresu
                 * punkt w ktorym moge dodac jakis short, to 12.5h plus czas rozpoczecia long haul
                 * sprawdzam ile jest czasu pomiedzy poczatkiem okresu, a moim punktem powyzej
                 * jesli ktorys z moich short haul tam sie miesci, to go dodaje z przodu
                 * jesli nie,to przesuwam long haul na pierwszy dzien jesli sie da
                 *
                 *
                 * powinno dodac tyle short az wystarczy czasu period
                 * */

        }
        return selectedFlights;
    }

    public List<List<Flight>> fillPeriodWithMixOfShortAndLong(Period period) {
        List<Flight> availableFlights = generateFlightsForPeriod(period);
        List<Flight> selectedFlights = new ArrayList<>();
        List<List<Flight>> allCombinations = new ArrayList<>();
        Duration longHaulMinDuration = Duration.ofHours(14);
        Duration requiredBreakAfterShort = Duration.ofHours(12).plusMinutes(30);
        Duration periodDuration = Duration.between(period.getStartTime(), period.getEndTime());

        List<Flight> possibleShortHauls = availableFlights.stream()
                .filter(flight -> flight.getFlightDuration().compareTo(longHaulMinDuration) < 0)
                .toList();

        List<Flight> possibleLongHauls = availableFlights.stream()
                .filter(flight -> flight.getFlightDuration().compareTo(longHaulMinDuration) >= 0)
                .toList();

        for (Flight shortHaul : possibleShortHauls) {
            LocalDateTime clearTimeForShortHaul = shortHaul.getClearTime().plus(requiredBreakAfterShort);
            for (Flight longHaul : possibleLongHauls) {
                if (clearTimeForShortHaul.isBefore(longHaul.getReportTime()) && longHaul.getClearTime()
                        .isBefore(period.getEndTime())) {
                    selectedFlights.add(shortHaul);
                    selectedFlights.add(longHaul);
                    allCombinations.add(selectedFlights);

                }
            }
        }
        return allCombinations;
    }

    public List<Flight> chooseCombinationOfFlights(List<List<Flight>> combinationOfFlights) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < combinationOfFlights.size(); i++) {
            System.out.println("List " + (i + 1) + ": ");
            for (Flight flight : combinationOfFlights.get(i)) {
                System.out.println(flight);
            }
        }

        System.out.println("Enter the number of list you want to choose:");
        int listIdNum = -1;
        while (listIdNum < 1 || listIdNum > combinationOfFlights.size()) {
            try {
                listIdNum = Integer.parseInt(scanner.nextLine());
                if (listIdNum < 1 || listIdNum > combinationOfFlights.size()) {
                    System.out.println("Invalid number.");
                    System.out.println("Enter number between 1 and " + combinationOfFlights.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, enter a number");
            }
        }
        return combinationOfFlights.get(listIdNum - 1);
    }


    public List<Flight> getFlights() {
        return flights;
    }

    void setToday(LocalDate today) {
        this.today = today;
    }
}
