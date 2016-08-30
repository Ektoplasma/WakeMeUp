package com.wakemeup.ektoplasma.valou.wakemeup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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
        mainListView = (ListView) findViewById( R.id.listMessage );
        ArrayList<String> messagelist = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.message_row, messagelist);
        listAdapter.add( "Paul : Pas mal ton reveil" );
        listAdapter.add( "Pluto : Mec... Really ?" );

        mainListView.setAdapter( listAdapter );
    }
}