package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;
import com.wakemeup.ektoplasma.valou.wakemeup.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Valentin on 30/08/2016.
 */
public class MessageActivity  extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        ArrayList<String> listMsg;
        ArrayList<String> listSender;
        mainListView = (ListView) findViewById( R.id.listMessage );
        ArrayList<String> messagelist = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.message_row, messagelist);

        if(Caller.getNewMessages() != null)
            listMsg = new ArrayList<String>(Caller.getNewMessages());
        else listMsg = new ArrayList<String>();

        if(Caller.getNewSenders() != null)
            listSender = new ArrayList<String>(Caller.getNewSenders());
        else listSender = new ArrayList<String>();

        Iterator<String> x = listMsg.iterator();
        Iterator<String> y = listSender.iterator();

        while (x.hasNext() && y.hasNext())
        {
            String message = (String) x.next();
            String pseudo = (String) y.next();
            listAdapter.add( pseudo + " : " + message );
        }
        
        mainListView.setAdapter( listAdapter );
    }
}