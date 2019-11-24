package com.example.magal.tennistracker;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mydb;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        listview = (ListView) findViewById(R.id.listview);
        mydb = new DatabaseHelper(this);

        populatelistview();
    }

    private void populatelistview() {

        Log.d(TAG, "populatelistview : displaying data");

        //get data and append to a list
        Cursor data = mydb.getJoueursData();
        ArrayList<String> listdata = new ArrayList<>();
        while(data.moveToNext()){
            listdata.add(data.getString(1));
        }

        ListAdapter listadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listdata);
        listview.setAdapter(listadapter);

    }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
