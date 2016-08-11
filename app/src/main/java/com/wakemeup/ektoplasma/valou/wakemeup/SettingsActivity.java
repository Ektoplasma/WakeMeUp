package com.wakemeup.ektoplasma.valou.wakemeup;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Valentin on 11/08/2016.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_screen);

    }
}
