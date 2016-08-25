package com.wakemeup.ektoplasma.valou.wakemeup;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Valentin on 03/08/2016.
 */
public class UsersList extends Fragment {

    HashMap<String, java.util.List<String>> UsersCategory;
    List<String> ListUsers;
    private ExpandableListView ExpList;
    UsersAdapter adapter;
    private SearchView searchV;
    //private String[] Userstring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ExpList = (ExpandableListView) view.findViewById(R.id.expandableListPerson);
       // Userstring = getResources().getStringArray(R.array.);
        setList();
        adapter = new UsersAdapter(getActivity(), UsersCategory, ListUsers);
        ExpList.setAdapter(adapter);
        registerForContextMenu(ExpList);

        /*******************************************************
         * Listener pour le click sur la liste
         ******************************************************/

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

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchV = (SearchView) view.findViewById(R.id.inputSearch);
        searchV.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchV.setIconifiedByDefault(false);
        searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filterData(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterData(newText);
                return false;
            }
        });
       //searchV.setOnCloseListener(this);

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
        String autorisation = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("prefWhoWakeMe", null);

        if(autorisation != null)
            UsersCategory = DataList.getData(autorisation);
        else
            UsersCategory = DataList.getData("Tout le monde");
        ListUsers = new ArrayList<String>(UsersCategory.keySet());
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

}
