package com.wakemeup.ektoplasma.valou.wakemeup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Valentin on 15/07/2016.
 */
public class Test extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    private void preparation_liste(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Amis");
        listDataHeader.add("Autres");

        List<String> amis = new ArrayList<String>();
        amis.add("Valou");
        amis.add("Theo");

        List<String> autres = new ArrayList<String>();
        autres.add("Jean");
        autres.add("Billy");

        listDataChild.put(listDataHeader.get(0), amis);
        listDataChild.put(listDataHeader.get(1), autres);
    }
}
