package org.example.services;

import com.sun.net.httpserver.Request;
import org.example.entities.Event;

import java.util.List;

public class RequestFactory {

    public List<Request> createRequests(List<Event> events) {
        //przeglada eventy i laczy je wg ich priorytetow i dat sklejajac i opakowując w requesty
        //w jednym requescie moga byc tylko eventy o tym samym priorytecie
        //w jednym requescie moga byc tylko eventy których nie dzieli jeden pelny dzien
        return null;
    }
}
