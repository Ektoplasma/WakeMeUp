package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wakemeup.ektoplasma.valou.wakemeup.R;

/**
 * Created by Valentin on 27/11/2016.
 */

public class MessengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent myIntent = getIntent(); // gets the previously created intent
        String Name = myIntent.getStringExtra("Name");
        setTitle(Name);
    }

}
