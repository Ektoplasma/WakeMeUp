package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.adaptaters.CustomAdapterMessage;
import com.wakemeup.ektoplasma.valou.wakemeup.adaptaters.CustomAdapterMessenger;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.MessengerClass;

import java.util.ArrayList;

/**
 * Created by Valentin on 27/11/2016.
 */

public class MessengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        ListView mainListView = (ListView) findViewById(R.id.listMessenger);
        Intent myIntent = getIntent(); // gets the previously created intent
        String Name = myIntent.getStringExtra("Name");
        setTitle(Name);
        ArrayList<MessengerClass> test = new ArrayList<MessengerClass>();
        MessengerClass bonjour = new MessengerClass("Bonjour", false);
        test.add(0, bonjour);
        MessengerClass bonjour1 = new MessengerClass("ca va", false);
        test.add(1, bonjour1);
        MessengerClass bonjour2 = new MessengerClass("Oui et toi", true);
        test.add(2, bonjour2);
        MessengerClass bonjour3 = new MessengerClass("Oui", false);
        test.add(3, bonjour3);

        System.out.print("Bonjour -> "+test.get(3).body);

        CustomAdapterMessenger adapter = new CustomAdapterMessenger(test, this);
        mainListView.setAdapter(adapter);
    }

}
