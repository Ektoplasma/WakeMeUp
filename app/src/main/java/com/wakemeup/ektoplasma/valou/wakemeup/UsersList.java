package com.wakemeup.ektoplasma.valou.wakemeup;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ExpList = (ExpandableListView) view.findViewById(R.id.expandableListPerson);
        UsersCategory = DataList.getData();
        ListUsers = new ArrayList<String>(UsersCategory.keySet());
        adapter = new UsersAdapter(getActivity(), UsersCategory, ListUsers);
        ExpList.setAdapter(adapter);
        ExpList.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                System.out.println("Bonjour");
                return false;
            }
        });
        return view;
    }

}
