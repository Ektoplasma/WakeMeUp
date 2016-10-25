package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.R.attr.defaultValue;
import static com.wakemeup.ektoplasma.valou.wakemeup.R.styleable.LoadingImageView_imageAspectRatio;
import static com.wakemeup.ektoplasma.valou.wakemeup.R.styleable.View;

/**
 * Created by Valentin on 11/08/2016.
 */
public class SettingsActivity extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private LinearLayout rootView;

    private LinearLayout titleView;

    private ListView preferenceView;

    private TextView textView;

    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;

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
        String photoPath = sp.getString("imagepath", "/sdcard/imh.jpeg");

        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        FrameLayout fl = (FrameLayout) inflater.inflate(R.layout.settings_img,null);

        if (!photoPath.equals("/sdcard/imh.jpeg")) {
            Log.d("ICI",photoPath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

            ImageView image_capture1 = (ImageView) fl.findViewById(R.id.img_prefview);

            image_capture1.setImageBitmap(bitmap);
            image_capture1.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        Preference myPref = (Preference) findPreference("img_pref");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
                return true;
            }
        });



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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String picturePath = data.getStringExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            }
        }

        if (requestCode == RESULT_CROP ) {
            if(resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                // Set The Bitmap Data To ImageView
                ImageView image_capture1 =  (ImageView)findViewById(R.id.img_prefview);
                image_capture1.setImageBitmap(selectedBitmap);
                image_capture1.setScaleType(ImageView.ScaleType.FIT_XY);

                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/crop_images");
                myDir.mkdirs();
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String fname = "Image-" + n + ".jpg";
                File file = new File(myDir, fname);
                Log.i("settingsactivity", "" + file);
                if (file.exists())
                    file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor edit=shre.edit();
                edit.putString("imagepath",myDir.getAbsolutePath() + "/"+fname);
                edit.commit();

            }
        }
    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
