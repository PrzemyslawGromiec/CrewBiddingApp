package org.example.services;

import org.example.entities.Preference;

public class PreferencesService {
    private Preference preference = new Preference();

    public Preference getModifiablePreferences() {
        return preference;
    }
}
