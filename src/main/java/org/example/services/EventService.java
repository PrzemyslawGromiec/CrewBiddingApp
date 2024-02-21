package org.example.services;

import org.example.entities.Event;
import org.example.general.Time;
import org.example.repositories.EventRepository;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class EventService {
    private Scanner scanner = new Scanner(System.in);
    private EventRepository eventRepository;
    private Time time = Time.getTime();

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addAllEvents() {
        List<Event> newEvents = new ArrayList<>();
        do {
            try {
                Event newEvent = createEvent();
                if (isOverlapping(newEvent)) {
                    System.out.println("This event overlaps with existing event.");

                } else {
                    if (isSameDayDifferentTime(newEvent)) {
                        System.out.println("Warning: this event is at the same day as existing event.");
                    }
                    System.out.println("Event added successfully.");
                    newEvents.add(newEvent);
                }

            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        } while (doYouWantContinue());
        showEvents();
        eventRepository.saveEvents(newEvents);
    }

    private LocalDateTime generateEventDateTime(int dayOfMonth, LocalTime hour) {
        LocalDate twoMonthsAhead = time.nextMonthTime().withDayOfMonth(dayOfMonth);
        return LocalDateTime.of(twoMonthsAhead, hour);
    }

    private Event createEvent() {
        //TODO: wpisujac dzien istnieje opcja zabookowania calego dnia lub od razu dodanie godziny

        Month eventMonth = time.nextMonthTime().getMonth();
        System.out.println("Remember that you're adding events for the next month: " + eventMonth);
        System.out.println("Add event description below:");
        String description = scanner.nextLine();

        System.out.println("Which day of " + eventMonth + " are you interested in?");
        System.out.println("Enter the start day of your event (as a number):");
        int startDay = getValidOption();

        System.out.println("Enter the end day of your event (as a number):");
        int endDay = getValidOption();
        System.out.println("Is your event reoccurring ?");
        System.out.println("type \"yes\" or \"no\"");
        String answer = scanner.nextLine();


        Map<String, LocalTime> times = getEventTime();
        LocalTime startTime = times.get("startTime");
        LocalTime endTime = times.get("endTime");

        LocalDateTime startDateTime = generateEventDateTime(startDay, startTime);
        LocalDateTime endDateTime = generateEventDateTime(endDay, endTime);
        int priority = getEventPriority();
        int eventId = eventRepository.getNextId();

        Event createdEvent = new Event(eventId, startDateTime, endDateTime, priority, description);
        if (answer.equalsIgnoreCase("yes")) {
            createdEvent.setReoccurring(true);
        }
        return createdEvent;
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
        for (Event existingEvent : eventRepository.getEvents()) {
            if (event.overlapsWith(existingEvent)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameDayDifferentTime(Event newEvent) {
        for (Event exitingEvent : eventRepository.getEvents()) {
            if (exitingEvent.isAtThatDayDifferentTime(newEvent)) {
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
        List<Event> events = eventRepository.getEvents();
        if (!events.isEmpty()) {
            System.out.println("List of all your events:");
            for (Event event : events) {
                System.out.println(event);
            }
        } else {
            System.out.println("List of events is empty.");
        }
    }

    public void deleteById() {
        System.out.println("Enter event ID number to delete from database:");
        int userInput = getValidOption();
        if (eventRepository.removeById(userInput).isPresent()) {
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Event with that ID number doesn't exist.");
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

    public List<Event> getEvents() {
        return eventRepository.getEvents();
    }
}
