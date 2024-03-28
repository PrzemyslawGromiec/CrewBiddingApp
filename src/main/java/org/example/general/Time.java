package org.example.general;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Time {
    private LocalDate today = LocalDate.now().plusMonths(0);
    private static Time time = new Time();

    private Time() {
    }

    public LocalDate nextMonthLocalDate() {
        return today.plusMonths(1);
    }

    public LocalDateTime nextMonthTime() {
        return nextMonthLocalDate().atTime(LocalTime.MIN);
    }

    public static Time getTime() {
        return time;
    }
}
