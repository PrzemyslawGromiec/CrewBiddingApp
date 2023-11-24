package org.example.repositories;

import org.example.entities.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventBinRepository implements EventRepository{

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
}

