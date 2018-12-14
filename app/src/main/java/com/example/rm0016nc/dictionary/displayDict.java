package com.example.rm0016nc.dictionary;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class displayDict extends AppCompatActivity {
    databaseHelper myDB;
    ArrayList<Dicts> dictList;
    Dicts dict;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_words);

        myDB = new databaseHelper(this);

        dictList = new ArrayList<Dicts>();
        Cursor data =  myDB.getAllData();

        if(data.getCount() == 0){
            Toast.makeText(this,"The database was empty",Toast.LENGTH_LONG).show();
        } else{
            while (data.moveToNext()){
                dict = new Dicts(data.getString(1) +" :", data.getString(2));
                dictList.add(dict);

            }
            TwoColumns_ListAdapter adapter = new TwoColumns_ListAdapter(this, R.layout.list_adapter_view, dictList);
            listView = (ListView) findViewById(R.id.display_listview);
            listView.setAdapter(adapter);
        }
    }
}
