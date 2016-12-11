package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.adaptaters.CustomAdapterMessage;
import com.wakemeup.ektoplasma.valou.wakemeup.adaptaters.CustomAdapterMessenger;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.MessengerClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Valentin on 27/11/2016.
 */

public class MessengerActivity extends AppCompatActivity {

    String Name;

    Caller messengerCall;

    ArrayList<MessengerClass> messageList;

    File cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        ListView mainListView = (ListView) findViewById(R.id.listMessenger);
        Intent myIntent = getIntent(); // gets the previously created intent
        Name = myIntent.getStringExtra("Name");
        setTitle(Name);
        messageList = new ArrayList<MessengerClass>();
       /* MessengerClass bonjour = new MessengerClass("Bonjour", false);
        messageList.add(0, bonjour);
        MessengerClass bonjour1 = new MessengerClass("ca va", false);
        messageList.add(1, bonjour1);
        MessengerClass bonjour2 = new MessengerClass("Oui et toi", true);
        messageList.add(2, bonjour2);
        MessengerClass bonjour3 = new MessengerClass("Oui", false);
        messageList.add(3, bonjour3);*/


        cache = new File(getCacheDir(), Name+"_cache");
        //cache.delete();

       /* SaveInCache("valou : Bonjour\n");
        SaveInCache("valou : Bien?\n");
        SaveInCache("Theo : Oui et toi\n");
        SaveInCache("valou : Oui\n");*/
        messageList = ReadCache(Name+"_cache");
        CustomAdapterMessenger adapter = new CustomAdapterMessenger(messageList, this);
        mainListView.setAdapter(adapter);

    }

    private ArrayList<MessengerClass> ReadCache(String cache_name)
    {
        ArrayList<MessengerClass> tmpList = new ArrayList<MessengerClass>();
        MessengerClass tmpMess = new MessengerClass("Bonjour", false);
        //cache = new File(getCacheDir(), cache_name+"_cache");
        int compt = 0;

        System.out.println("Bonjour -> "+cache.getAbsolutePath());

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(cache));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
                if(line.startsWith(Name+" : "))
                {
                    tmpMess = new MessengerClass(line.replace(Name+" : ", ""), false);
                    tmpList.add(compt, tmpMess);
                }
                else
                {
                    tmpMess = new MessengerClass(line.replace(messengerCall.getPseudonyme()+" : ", ""), true);
                    tmpList.add(compt, tmpMess);
                }
                compt++;
            }
            br.close();
        }
        catch (IOException e) {
            Log.e("Exception", "Error read cache: " + e.toString());
        }
        System.out.println("Bonjour -> Cache : "+text);

        return tmpList;
    }

    private void SaveInCache(String write)
    {
        //cache = new File(getCacheDir(), cache_name+"_cache");

        try
        {
            FileOutputStream fOut = new FileOutputStream(cache, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(write);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "Error write cache: " + e.toString());
        }
    }

}
