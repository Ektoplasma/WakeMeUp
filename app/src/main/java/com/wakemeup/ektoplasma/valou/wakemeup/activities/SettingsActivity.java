package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;

/**
 * Created by Valentin on 11/08/2016.
 */
public class SettingsActivity extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private LinearLayout rootView;

    private LinearLayout titleView;

    private ListView preferenceView;

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*addPreferencesFromResource(R.xml.settings_screen);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        EditTextPreference editTextPref = (EditTextPreference) findPreference("prefReveilDefault");
        editTextPref
                .setSummary(sp.getString("prefReveilDefault", "Lien YouTube"));
        ListPreference lp = (ListPreference) findPreference("prefWhoWakeMe");
        lp.setSummary(sp.getString("prefWhoWakeMe", "Tout le monde"));*/

        rootView = new LinearLayout(this);

        rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        rootView.setOrientation(LinearLayout.VERTICAL);

        textView = new TextView(this);

        textView.setText(R.string.toto);

        preferenceView = new ListView(this);

        preferenceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        preferenceView.setId(android.R.id.list);



        PreferenceScreen screen = createPreferenceHierarchy();

        screen.bind(preferenceView);

        preferenceView.setAdapter(screen.getRootAdapter());

        rootView.addView(textView);

        rootView.addView(preferenceView);

        this.setContentView(rootView);

        setPreferenceScreen(screen);

    }

    private PreferenceScreen createPreferenceHierarchy() {

        // Root

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());



        // Inline preferences

        PreferenceCategory cat1 = new PreferenceCategory(this);

        cat1.setTitle("Cat 1");

        root.addPreference(cat1);



        for(int i = 0; i < 5; i ++)

        {

            // Toggle preference

            CheckBoxPreference togglePref = new CheckBoxPreference(this);

            togglePref.setKey("toggle_check" + Integer.toString(i));

            togglePref.setTitle("Toggle Me");

            togglePref.setChecked(prefs.getBoolean("toggle_check" + Integer.toString(i), false));

            togglePref.setSummary("Checkbox " + Integer.toString(i));

            cat1.addPreference(togglePref);

        }



        // Inline preferences

        PreferenceCategory cat2 = new PreferenceCategory(this);

        cat2.setTitle("Cat 2");

        root.addPreference(cat2);



        for(int i = 6; i < 13; i ++)

        {

            // Toggle preference

            CheckBoxPreference togglePref = new CheckBoxPreference(this);

            togglePref.setKey("toggle_check" + Integer.toString(i));

            togglePref.setTitle("Toggle Me");

            togglePref.setChecked(prefs.getBoolean("toggle_check" + Integer.toString(i), false));

            togglePref.setSummary("Checkbox " + Integer.toString(i));

            cat2.addPreference(togglePref);

        }

        PreferenceCategory cat3 = new PreferenceCategory(this);
        root.addPreference(cat3);
        EditTextPreference truc = new EditTextPreference(this);
        truc.setKey("edittext");
        truc.setTitle("Edit Me");

        cat3.addPreference(truc);


        return root;

    }

    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Preference pref = findPreference(key);
        String oldPref = (String)pref.getSummary();
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());
        }
        if (pref instanceof ListPreference) {
            ListPreference listp = (ListPreference) pref;
            pref.setSummary(listp.getEntry());
            if(key.equals("prefWhoWakeMe")){
                String newPref = (String) pref.getSummary();
                if(newPref.equals("Tout le monde"))
                {
                    Caller.newPref("world");
                }
                else if(newPref.equals("Seulement moi"))
                {
                    Caller.newPref("private");
                }
                else {
                    Caller.newPref("friends");
                }

            }
        }
    }

}
