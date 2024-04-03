package bidding.crew.service;

import bidding.crew.entity.Preference;

public class PreferencesService {
    private Preference preference = new Preference();

    public Preference getModifiablePreferences() {
        return preference;
    }
}
