package com.wakemeup.ektoplasma.valou.wakemeup;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Valentin on 03/08/2016.
 */
public class DataList {

    public static HashMap<String, List<String>> getData(String autorisation)
    {
        HashMap<String, List<String>> UsersDetails = new HashMap<String, List<String>>();

        //version avec volley : non testée donc en commentaire
        Caller.setCookieInstance("abc");
        Caller.getBddAmi();
        List<String> Amis = Caller.getAmi();
        List<String> ToutLeMonde = new ArrayList<String>();

        //List<String> Amis = new ArrayList<String>();
        //Amis.add("Valou");
        //Amis.add("Théo");

        SettingsActivity settings = new SettingsActivity();

        UsersDetails.put("Amis", Amis);


        if(autorisation.equals("Tout le monde"))
        {
            ToutLeMonde.add("Jean");
            ToutLeMonde.add("Billy");
            ToutLeMonde.add("Il est drole lui");
            UsersDetails.put("Tous les utilisateurs", ToutLeMonde);
        }
        else if(UsersDetails.get(ToutLeMonde) != null)
        {
            UsersDetails.remove(ToutLeMonde);
        }

        return UsersDetails;
    }

}
