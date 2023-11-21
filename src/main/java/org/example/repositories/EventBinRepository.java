package org.example.repositories;

import org.example.entities.Event;

import java.io.*;
import java.util.List;

public class EventBinRepository implements EventRepository{

    private static final String FILE_NAME = "events.bin";

    @Override
    public List<Event> getEvents() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_NAME));
            List<Event> events = (List<Event>) objectInputStream.readObject();
            objectInputStream.close();
            return events;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveEvents(List<Event> events) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            objectOutputStream.writeObject(events);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
            //TODO:refactor
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
