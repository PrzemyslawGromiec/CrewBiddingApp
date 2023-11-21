package org.example.services;

import org.example.entities.Event;
import org.example.entities.Period;
import org.example.repositories.EventRepository;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class EventService {
    private List<Event> events;
    private List<Period> periods;
    private Scanner scanner = new Scanner(System.in);
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.events = eventRepository.getEvents();
        this.periods = new ArrayList<>();
        this.eventRepository = eventRepository;
    }

    public void addAllEvents() {
        //TODO: mozliwosc kasowania eventow procz ich odczytywania

        while (doYouWantContinue()) {
            try {

                Event newEvent = createEvent();
                //TODO: ostrzezenie dla uzytkownika ze wydarzenia sa tego samego dnia o innych porach
                if (isOverlapping(newEvent)) {
                    System.out.println("This event overlaps with existing event.");
                } else {
                    events.add(newEvent);
                    System.out.println("Event added successfully.");
                }

            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        showEvents();
        eventRepository.saveEvents(events);
    }

    private LocalDateTime generateEventDateTime(int dayOfMonth, LocalTime time) {
        LocalDate twoMonthsAhead = LocalDate.now().plusMonths(1).withDayOfMonth(dayOfMonth);
        return LocalDateTime.of(twoMonthsAhead, time);
    }

    private Event createEvent() {
        //TODO: wpisujac dzien istnieje opcja zabookowania calego dnia lub od razu dodanie godziny
        Month eventMonth = LocalDate.now().plusMonths(1).getMonth();
        System.out.println("Remember that you're adding events for the next month: " + eventMonth);
        System.out.println("Add event description below:");
        String description = scanner.nextLine();

        System.out.println("Which day of " + eventMonth + " are you interested in?");
        System.out.println("Enter the start day of your event (as a number):");
        int startDay = getValidOption();

        System.out.println("Enter the end day of your event (as a number):");
        int endDay = getValidOption();

        Map<String, LocalTime> times = getEventTime();
        LocalTime startTime = times.get("startTime");
        LocalTime endTime = times.get("endTime");

        LocalDateTime startDateTime = generateEventDateTime(startDay, startTime);
        LocalDateTime endDateTime = generateEventDateTime(endDay, endTime);
        int priority = getEventPriority();

        return new Event(startDateTime, endDateTime, priority, description);
    }


    private Map<String, LocalTime> getEventTime() {
        Map<String, LocalTime> times = new HashMap<>();
        System.out.println("Enter the time the event starts in a format of (HH:mm) or type 'full' for all day:");
        String timeInput = scanner.nextLine();

        if (timeInput.equalsIgnoreCase("full")) {
            times.put("startTime", LocalTime.MIN);
            times.put("endTime", LocalTime.MAX);
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(timeInput, timeFormatter);
            times.put("startTime", startTime);

            System.out.println("Enter the time the event ends in a format of (HH:mm):");
            timeInput = scanner.nextLine();
            LocalTime endTime = LocalTime.parse(timeInput, timeFormatter);
            times.put("endTime", endTime);
        }
        return times;
    }

    private int getEventPriority() {
        while (true) {
            System.out.println("Enter event priority (min 1, max 3):");
            int priority = getValidOption();
            if (priority >= 1 && priority <= 3) {
                return priority;
            } else {
                System.out.println("Invalid number.");
            }
        }
    }

    private boolean isOverlapping(Event event) {
        for (Event existingEvent : events) {
            if (event.overlapsWith(existingEvent)) {
                return true;
            }
        }
        return false;
    }

    private boolean doYouWantContinue() {
        System.out.println("Do you want to add another event? (yes/no)");
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("yes")) {
            return true;
        } else if (userInput.equalsIgnoreCase("no")) {
            return false;
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'");
            return doYouWantContinue();
        }
    }

    public void showEvents() {
        if (!events.isEmpty()) {
            System.out.println("List of all your events:");
            for (Event event : events) {
                System.out.println(event);
            }
        } else {
            System.out.println("List of events is empty.");
        }
    }

    public void deleteByDescription() {
        //TODO: usuwanie po id eventu
        System.out.println("In order to delete event, type its description below:");
        String description = scanner.nextLine();
        if(events.removeIf(event -> event.getDescription().equalsIgnoreCase(description))) {
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Event with that description doesn't exist.");
        }
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

    public boolean isUserInputValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public int getValidOption() {
        String userInput;
        while (true) {
            userInput = scanner.nextLine();

            if (isUserInputValid(userInput)) {
                return Integer.parseInt(userInput);
            } else {
                System.out.println("Invalid input. Please enter a number!");
            }
        }
    }

}
