package org.example.services;

import org.example.entities.Event;
import org.example.entities.EventRequest;
import org.example.entities.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RequestFactory {

    //przeglada eventy i laczy je wg ich priorytetow i dat sklejajac i opakowując w requesty
    //w jednym requescie moga byc tylko eventy o tym samym priorytecie
    //w jednym requescie moga byc tylko eventy których nie dzieli jeden pelny dzien

    public List<Request> createRequests(List<Event> events) {

        List<Request> requests = new ArrayList<>();
        events.sort(Comparator.comparing(Event::getStart));
        List<List<Event>> groupedEvents = new ArrayList<>();

        for (Event event : events) {
            if (groupedEvents.isEmpty()) {
                groupedEvents.add(new ArrayList<>());
                groupedEvents.get(0).add(event);
            } else {
                List<Event> lastGroup = groupedEvents.get(groupedEvents.size() - 1);
                Event lastEventInGroup = lastGroup.get(lastGroup.size() - 1);

                if (event.getPriority() == lastEventInGroup.getPriority() &&
                        !event.getStart().toLocalDate().isAfter(lastEventInGroup.getEnd().toLocalDate().plusDays(1))) {
                    lastGroup.add(event);
                } else {
                    List<Event> newGroup = new ArrayList<>();
                    newGroup.add(event);
                    groupedEvents.add(newGroup);
                }
            }
        }
        for (List<Event> group : groupedEvents) {
            requests.add(new EventRequest(group));
        }
        return requests;
    }
}
