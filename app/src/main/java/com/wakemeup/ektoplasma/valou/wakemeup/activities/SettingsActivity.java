package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.SharedPreferences;
import android.content.res.Resources;
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

import static android.R.attr.defaultValue;

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

        addPreferencesFromResource(R.xml.settings_screen);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        EditTextPreference editTextPref = (EditTextPreference) findPreference("prefReveilDefault");
        editTextPref
                .setSummary(sp.getString("prefReveilDefault", "Lien YouTube"));
        ListPreference lp = (ListPreference) findPreference("prefWhoWakeMe");
        lp.setSummary(sp.getString("prefWhoWakeMe", "Tout le monde"));

        /*rootView = new LinearLayout(this);

        rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        rootView.setOrientation(LinearLayout.VERTICAL);

       /* textView = new TextView(this);

        textView.setText(R.string.toto);

        preferenceView = new ListView(this);

        preferenceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        preferenceView.setId(android.R.id.list);



        PreferenceScreen screen = createPreferenceHierarchy();

        screen.bind(preferenceView);

        preferenceView.setAdapter(screen.getRootAdapter());

//        rootView.addView(textView);

        rootView.addView(preferenceView);

        this.setContentView(rootView);

        setPreferenceScreen(screen);*/

    }

    private PreferenceScreen createPreferenceHierarchy() {

        // Root

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName("my_preferences");
        prefMgr.setSharedPreferencesMode(MODE_MULTI_PROCESS);

        /*PREF USER*/

        PreferenceCategory prefuser = new PreferenceCategory(this);

        prefuser.setTitle("Preference utilisateur");

        root.addPreference(prefuser);

        EditTextPreference username = new EditTextPreference(this);

        username.setTitle("Le nom utilisateur");

        username.setSummary("Le nom de la bdd");

        username.setSelectable(false);

        prefuser.addPreference(username);

        /*PREF REVEIL*/

        PreferenceCategory prefreveil = new PreferenceCategory(this);

        prefreveil.setTitle("Preference du reveil");

        root.addPreference(prefreveil);

        CheckBoxPreference historique = new CheckBoxPreference(this);

        historique.setTitle("Historique de vote");

        historique.setChecked(true);

        historique.setSummary("Savoir qui a vote pour nous");

        prefreveil.addPreference(historique);

        ListPreference autorisation = new ListPreference(this);

        autorisation.setTitle("Qui peut voter pour nous");

        Resources res = getResources();

        autorisation.setEntries(res.getStringArray(R.array.syncWhoWakeMe));

        prefreveil.addPreference(autorisation);

        EditTextPreference reveildef = new EditTextPreference(this);

        reveildef.setTitle("Reveil par default");

        reveildef.setSummary("Le lien YouTube");

        prefreveil.addPreference(reveildef);

        /*PREF A PROPROS*/

        PreferenceCategory prefapropos = new PreferenceCategory(this);

        prefapropos.setTitle("A propos");

        root.addPreference(prefapropos);

        EditTextPreference version = new EditTextPreference(this);

        version.setTitle("Version");

        version.setSummary("1.0");

        version.setSelectable(false);

        prefapropos.addPreference(version);

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

        SharedPreferences.Editor editor = sharedPreferences.edit();
        System.out.println("ICI VALUE ->"+sharedPreferences.getBoolean("prefHistory", false));
        editor.putBoolean("prefHistory",sharedPreferences.getBoolean("prefHistory", false));
        editor.commit();
    }

}
