package org.example.repositories;

import org.example.entities.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventBinRepository implements EventRepository {

    private static final String FILE_NAME = "events.bin";
    private int nextId = 0;


    private List<Event> loadEvents() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                nextId = objectInputStream.readInt();
                List<Event>  events = (List<Event>) objectInputStream.readObject();
                objectInputStream.close();
                return events;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Event> getEvents() {
        return new ArrayList<>(loadEvents());
    }

    @Override
    public void saveEvents(List<Event> events) {
        events.addAll(loadEvents());
        for (Event event : events) {
            if (event.getId() == 0) {
                event.setId(++nextId);
            }
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            objectOutputStream.writeInt(nextId);
            objectOutputStream.writeObject(new ArrayList<>(events));
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Error saving events", e);
        }
    }

    private Optional<Event> findById(int id) {
        for (Event event : getEvents()) {
            if (event.getId() == id) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Event> removeById(int id) {
        Optional<Event> optionalEvent = findById(id);
        if (optionalEvent.isPresent()) {
            Event eventToBeRemoved = optionalEvent.get();
            List<Event> events = getEvents();
            events.remove(eventToBeRemoved);
            saveEvents(events);
            return Optional.of(eventToBeRemoved);
        } else {
            return Optional.empty();
        }
    }

}

