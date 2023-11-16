package org.example.services;

import org.example.entities.Event;
import org.example.entities.Period;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class EventService {
    private List<Event> events;
    private List<Period> periods;
    private Scanner scanner = new Scanner(System.in);

    public EventService() {
        this.events = new ArrayList<>();
        this.periods = new ArrayList<>();
    }

    public void addEvent(Event event) {
        for (Event exisitingEvent : events) {
            if (event.overlapsWith(exisitingEvent)) {
                throw new IllegalArgumentException("Event overlaps with an existing event.");
            }
        }
        events.add(event);
    }

    public void addAllEvents() {
        boolean addingEvents = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        while (addingEvents) {
            try {
                System.out.println("Enter event start time");
                System.out.println("(in the format of DD-MM-YYYY HH:mm:");
                LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine(), formatter);
                System.out.println("Enter event end time:");
                LocalDateTime endTime = LocalDateTime.parse(scanner.nextLine(), formatter);
                System.out.println("Enter event priority:");
                int priority = Integer.parseInt(scanner.nextLine());
                System.out.println("Add event description below:");
                String description = scanner.nextLine();

                Event newEvent = new Event(startTime, endTime, priority, description);

                if (isOverlapping(newEvent)) {
                    System.out.println("This event overlaps with existing event.");
                } else {
                    events.add(newEvent);
                    System.out.println("Event added successfully.");
                }

                while (true) {
                    System.out.println("Do you want to add another event? (yes/no)");
                    String userInput = scanner.nextLine();
                    if (userInput.equalsIgnoreCase("yes")) {
                        break;
                    } else if (userInput.equalsIgnoreCase("no")) {
                        addingEvents = false;
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        showEvents();
    }

    private void showEvents() {
        if (!events.isEmpty()) {
            System.out.println("List of all your events:");
            for (Event event : events) {
                System.out.println(event);
            }
        } else {
            System.out.println("No events added.");
        }
    }

    public boolean isOverlapping(Event event) {
        for (Event existingEvent : events) {
            if (event.overlapsWith(existingEvent)) {
                return true;
            }
        }
        return false;
    }

    public void deleteEvent(int index) {
        if (index < 0 || index >= events.size()) {
            throw new IndexOutOfBoundsException("Invalid index for deleting event.");
        }
        events.remove(index);
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public List<Event> getEventsByPriority(int priority) {
        return events.stream()
                .filter(event -> event.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public Duration getTotalPeriod() {
        if (events.isEmpty()) {
            return Duration.ZERO;
        }

        LocalDateTime earliestStart = events.get(0).getStart();
        LocalDateTime latestEnd = events.get(0).getEnd();

        for (Event event : events) {
            if (event.getStart().isBefore(earliestStart)) {
                earliestStart = event.getStart();
            }
            if (event.getEnd().isAfter(latestEnd)) {
                latestEnd = event.getEnd();
            }
        }
        return Duration.between(earliestStart, latestEnd);
    }

    public Set<LocalDate> getCoveredDays() {
        if (events.isEmpty()) {
            return new HashSet<>();
        }

        LocalDate earliestStart = events.get(0).getStart().toLocalDate();
        LocalDate latestEnd = events.get(0).getEnd().toLocalDate();
        for (Event event : events) {
            LocalDate startDate = event.getStart().toLocalDate();
            LocalDate endDate = event.getEnd().toLocalDate();
            if (startDate.isBefore(earliestStart)) {
                earliestStart = startDate;
            }
            if (endDate.isAfter(latestEnd)) {
                latestEnd = endDate;
            }
        }
        Set<LocalDate> coveredDates = new HashSet<>();
        LocalDate currentDate = earliestStart;
        while (!currentDate.isAfter(latestEnd)) {
            coveredDates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return coveredDates;
    }

    public void displayCoveredDays() {
        Set<LocalDate> coveredDates = getCoveredDays();
        for (LocalDate coveredDate : coveredDates) {
            System.out.println(coveredDate);
        }
    }

}
