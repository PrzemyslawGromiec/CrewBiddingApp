package bidding.crew.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class EventRequest extends Request {
    private List<Event> events;

    public EventRequest(List<Event> events) {
        this.events = new ArrayList<>(events);
        events.sort(Comparator.comparing(Event::getStart));
    }

    @Override
    public LocalDateTime getStartTime() {
        return events.get(0).getStart();
    }

    @Override
    public LocalDateTime getEndTime() {
        return events.get(events.size() - 1).getEnd();
    }

    @Override
    public int getNumberOfStars() {
        return events.get(0).getPriority();
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getEventDescription() {
        return events.get(0).getDescription();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM h:mma", Locale.ENGLISH);
        String formattedReport = getStartTime().format(formatter);
        String formattedClear = getEndTime().format(formatter);
        return "event -> " + "start: " + formattedReport +
                " ,end: " + formattedClear +
                " ,description: " + getEventDescription() + super.toString();
    }
}
