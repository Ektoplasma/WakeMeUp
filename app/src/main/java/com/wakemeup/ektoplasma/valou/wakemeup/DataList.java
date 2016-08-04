package com.wakemeup.ektoplasma.valou.wakemeup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Valentin on 03/08/2016.
 */
public class DataList {

    public static HashMap<String, List<String>> getData()
    {
        HashMap<String, List<String>> UsersDetails = new HashMap<String, List<String>>();
        List<String> Amis = new ArrayList<String>();
        Amis.add("Valou");
        Amis.add("Théo");

        List<String> ToutLeMonde = new ArrayList<String>();
        ToutLeMonde.add("Jean");
        ToutLeMonde.add("Billy");
        ToutLeMonde.add("Il est drole lui");

        UsersDetails.put("Amis", Amis);
        UsersDetails.put("Tous les utilisateurs", ToutLeMonde);

        return UsersDetails;
    }

}
