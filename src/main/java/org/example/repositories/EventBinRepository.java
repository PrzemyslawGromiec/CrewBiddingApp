package org.example.repositories;

import org.example.entities.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventBinRepository implements EventRepository {

    private static final String FILE_NAME = "events.bin";
    private int nextId = 0;
    private List<Event> events;

    public EventBinRepository() {
        loadEvents();
    }

    private void loadEvents() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                nextId = objectInputStream.readInt();
                this.events = (List<Event>) objectInputStream.readObject();
                objectInputStream.close();
            } else {
                this.events = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            this.events = new ArrayList<>();
        }
    }

    @Override
    public List<Event> getEvents() {
        return new ArrayList<>(this.events);
    }

    @Override
    public void saveEvents(List<Event> events) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            objectOutputStream.writeInt(nextId);
            objectOutputStream.writeObject(new ArrayList<>(events));
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Error saving events", e);
        }
    }

    public int getNextId() {
        return nextId++;
    }

    private Optional<Event> findById(int id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Event> removeById(int id) {
        //todo znalezc w events po id, skasowac, zaktualizować plik i zwrocic odpowiedni optional
        Optional<Event> optionalEvent = findById(id);
        if (optionalEvent.isPresent()) {
            Event eventToBeRemoved = optionalEvent.get();
            events.remove(eventToBeRemoved);
            saveEvents(events);
            return Optional.of(eventToBeRemoved);
        } else {
            return Optional.empty();
        }
    }

}

