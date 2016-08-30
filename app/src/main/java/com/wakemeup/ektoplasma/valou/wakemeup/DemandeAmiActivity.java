package com.wakemeup.ektoplasma.valou.wakemeup;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Valentin on 30/08/2016.
 */
public class DemandeAmiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demande_ami);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Valou");
        list.add("Théo");

        CustomAdapterDemandeAmi adapter = new CustomAdapterDemandeAmi(list, this);

        ListView lView = (ListView)findViewById(R.id.listDemandeAmi);
        lView.setAdapter(adapter);
    }
}
