package org.example.general;

import java.time.LocalDate;

public class Time {
    private LocalDate today = LocalDate.now().plusMonths(0);
    private static Time time = new Time();

    private Time() {
    }

    public LocalDate nextMonthTime() {
        return today.plusMonths(1);
    }

    public static Time getTime() {
        return time;
    }
}
