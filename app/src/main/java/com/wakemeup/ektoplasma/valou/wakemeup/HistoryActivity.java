package com.wakemeup.ektoplasma.valou.wakemeup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Valentin on 23/09/2016.
 */

public class HistoryActivity extends Fragment {

    private View view;
    private ArrayList<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        //TODO remplir la list avec historique

        return view;
    }


    @Override
    public void onResume()
    {
        super.onResume();
        list = new ArrayList<String>();
        NameYouTubeVideo jParser = new NameYouTubeVideo();
        String[] urls = new String[1];
        urls[0] = "https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=Sw9uicEGjGw&format=json";
        new NameYouTubeVideo().execute(urls);
        list.add("Bonjour");

        CustomAdapterHistory adapter = new CustomAdapterHistory(list, getActivity());

        ListView lView = (ListView)view.findViewById(R.id.ListHistory);
        lView.setAdapter(adapter);
    }
}
