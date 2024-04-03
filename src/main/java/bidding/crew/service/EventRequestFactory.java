package bidding.crew.service;

import bidding.crew.entity.Event;
import bidding.crew.entity.EventRequest;
import bidding.crew.general.Time;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventRequestFactory {
    private List<EventRequest> requests = new ArrayList<>();
    private Time time;

    public EventRequestFactory(Time time) {
        this.time = time;
    }

    public List<EventRequest> createRequests(List<Event> events) {
        List<Event> sortedAndFilteredEvents = events.stream()
                .filter(
                event -> event.getStart().getMonth() == time.nextMonthLocalDate().getMonth()
                        || event.getEnd().getMonth() == time.nextMonthLocalDate().getMonth()
                ).sorted(Comparator.comparing(Event::getStart)).toList();

        List<List<Event>> groupedEvents = new ArrayList<>();

        for (Event event : sortedAndFilteredEvents) {
            if (groupedEvents.isEmpty()) {
                groupedEvents.add(new ArrayList<>());
                groupedEvents.get(0).add(event);
                continue;
            }
            List<Event> lastGroup = groupedEvents.get(groupedEvents.size() - 1);
            Event lastEventInGroup = lastGroup.get(lastGroup.size() - 1);

            if (event.getPriority() == lastEventInGroup.getPriority() &&
                    !event.getStart().toLocalDate().isAfter(lastEventInGroup.getEnd().toLocalDate().plusDays(1))) {
                lastGroup.add(event);
                continue;
            }
            List<Event> newGroup = new ArrayList<>();
            newGroup.add(event);
            groupedEvents.add(newGroup);
        }
        for (List<Event> group : groupedEvents) {
            requests.add(new EventRequest(group));
        }
        return requests;
    }
}
