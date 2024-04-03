package bidding.crew.entity;

import bidding.crew.general.Time;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime start;
    private LocalDateTime end;
    private int priority;
    private String description;
    private int id;
    private boolean reoccurring = false;


    public Event(LocalDateTime start, LocalDateTime end, int priority, String description) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start date.");
        }
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.description = description;
    }

    public boolean overlapsWith(Event other) {
        return !getEnd().isBefore(other.getStart()) && !getStart().isAfter(other.getEnd());
    }

    public boolean isAtThatDayDifferentTime(Event other) {
        LocalDate thisDate = getStart().toLocalDate();
        LocalDate otherDate = other.getStart().toLocalDate();

        return thisDate.equals(otherDate) &&
                (getEnd().isBefore(other.getStart()) || getStart().isAfter(other.getEnd()));
    }

    public LocalDateTime getStart() {
        if (reoccurring) {
            start = withAppTime(start,0);
        }
        return start;
    }

    public LocalDateTime getEnd() {
        if (reoccurring) {
            if ((start.getMonth() != end.getMonth())) {
                end = withAppTime(end,1);
            } else {
                end = withAppTime(end,0);
            }
        }
        return end;
    }

    private LocalDateTime withAppTime(LocalDateTime time, int plusMonth) {
        return time.withMonth(Time.getTime().nextMonthLocalDate().plusMonths(plusMonth).getMonthValue());
    }
    public int getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setReoccurring(boolean reoccurring) {
        this.reoccurring = reoccurring;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id: " + id +
                " ,start = " + getStart() +
                ", end = " + getEnd() +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (priority != event.priority) return false;
        if (id != event.id) return false;
        if (reoccurring != event.reoccurring) return false;
        if (!Objects.equals(start, event.start)) return false;
        if (!Objects.equals(end, event.end)) return false;
        return Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + priority;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (reoccurring ? 1 : 0);
        return result;
    }
}
