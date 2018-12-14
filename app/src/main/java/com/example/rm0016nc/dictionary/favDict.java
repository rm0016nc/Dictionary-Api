package com.example.rm0016nc.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class favDict extends AppCompatActivity {
    database myDB;
    ArrayList<Favs> favList;
    Favs favs;
    ListView listView;
    Button emptyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_fav);
        myDB = new database(this);
        emptyBtn = (Button) findViewById(R.id.emptyBtn);

        favList = new ArrayList<Favs>();
        Cursor data =  myDB.getAllData();

        if(data.getCount() == 0){
            Toast.makeText(this,"The favorite bucket is empty",Toast.LENGTH_LONG).show();
        } else{
            while (data.moveToNext()){
                favs = new Favs(data.getString(1) , data.getString(2));
                favList.add(favs);
            }
            emptyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer deletedRows = myDB.resetData();
//                    listView.clearAnimation();
//                    Two_column_listAdapter adapter = new Two_column_listAdapter(favDict.this, R.layout.list_adapter_fav, favList);
//                    listView = (ListView) findViewById(R.id.listview);
                    listView.setAdapter(null);
                    if (deletedRows > 0){
                        Toast.makeText(favDict.this, "Reset successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(favDict.this, "Reset Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                }
            });
            Two_column_listAdapter adapter = new Two_column_listAdapter(this, R.layout.list_adapter_fav, favList);
            listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(adapter);

        }
    }
}