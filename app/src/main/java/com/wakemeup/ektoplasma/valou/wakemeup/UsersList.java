package com.wakemeup.ektoplasma.valou.wakemeup;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Valentin on 03/08/2016.
 */
public class UsersList extends Fragment {

    HashMap<String, java.util.List<String>> UsersCategory;
    List<String> ListUsers;
    private ExpandableListView ExpList;
    UsersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ExpList = (ExpandableListView) view.findViewById(R.id.expandableListPerson);
       // Userstring = getResources().getStringArray(R.array.);

        setList();

        adapter = new UsersAdapter(getActivity(), UsersCategory, ListUsers);
        ExpList.setAdapter(adapter);
        registerForContextMenu(ExpList);

        ExpList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        ExpList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String name = adapter.getChild(groupPosition, childPosition).toString();
                /*TODO
                        -message du voteur*/
                if(Caller.getCurrentLink() != null) Caller.setClockSong(name);
                else Toast.makeText(getActivity(), "S'il vous plaît, partagez un lien Youtube avant de cliquer ici.", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
       // adapter.notifyDataSetChanged();

        setList();

        adapter.updateUsersList(ListUsers);//clear
        registerForContextMenu(ExpList);
    }

    private void setList()
    {
        //TODO : setList threadé
        String autorisation = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("prefWhoWakeMe", null);

        if(autorisation != null)
            UsersCategory = getData(autorisation);
        else
            UsersCategory = getData("Tout le monde");
        ListUsers = new ArrayList<String>(UsersCategory.keySet());
        System.out.println("ici userscategory : " + UsersCategory);
        System.out.println("ici listusers : " + ListUsers);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

        String selection = adapter.getChild(groupPosition, childPosition).toString();

        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {

        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            menu.setHeaderTitle(selection);
            menu.add(0, 1, 1, "Réveiller");
            if(adapter.getGroup(groupPosition).toString() == "Tous les utilisateurs")
            {
                menu.add(1, 2, 2, "Ajouter");
            }
            else
            {
                menu.add(1, 3, 3, "Supprimer");
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case 1://Lancement App YouTube
                Intent LaunchIntent =  getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                startActivity(LaunchIntent);
                break;
            case 2://Ajout d'ami
                Toast.makeText(getActivity(), "Demande d'ajout envoyée", Toast.LENGTH_LONG).show();
                break;
            case 3://Supression ami
                Toast.makeText(getActivity(), "Ami retiré de la liste", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private HashMap<String, List<String>> getData(String autorisation)
    {

        HashMap<String, List<String>> UsersDetails = new HashMap<String, List<String>>();

        //version avec volley : non testée donc en commentaire
        Caller.setCookieInstance("abc");

        Caller.getBddAmi();

        List<String> Amis = Caller.getAmi();

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Liste -> "+Amis);



        while(UsersDetails.containsKey("Amis")) UsersDetails.remove("Amis");
        UsersDetails.put("Amis", Amis);

        List<String> ToutLeMonde = new ArrayList<String>();

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
        System.out.println(UsersDetails.values());

        return UsersDetails;
    }

}
