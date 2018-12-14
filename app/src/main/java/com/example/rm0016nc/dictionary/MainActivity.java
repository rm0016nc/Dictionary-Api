package com.example.rm0016nc.dictionary;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

//    Button requestApiButtonClick;
    TextView t1;
    EditText e1;
    EditText myDef;
    String word;
    Button trackBtn;
    Button favBtn;
    TextView wod;
    TextView error;
    Button savedBtn;
    Button cancelBtn;
    String def;
    String definition;
    ToggleButton clear;

    databaseHelper myDb;
    database db;

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.wordDef);
        error = (TextView) findViewById(R.id.error);
        e1 = (EditText) findViewById(R.id.e1);
        myDb = new databaseHelper(this);
        db = new database(this);
        trackBtn = (Button) findViewById(R.id.trackBtn);
        favBtn = (Button) findViewById(R.id.favBtn);
        clear = (ToggleButton) findViewById(R.id.clear);
        savedBtn = (Button) findViewById(R.id.savedBtn);

        clear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(clear.isChecked()){
                    e1.setText("");
                }else{
                    e1.setText("");
                }
            }
        });

        viewAll();
        viewSaved();
    }

    public void requestApiButtonClick (View v) {
        MyDictionaryRequest myDictionaryRequest = new MyDictionaryRequest(this, t1, e1, favBtn, def);
        final String language = "en";
        final String word = e1.getText().toString();
        if(word.matches("")){
            Toast.makeText(this,"Please enter a word", Toast.LENGTH_LONG).show();
        }
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        myDictionaryRequest.execute("https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id);
    }

    public void viewAll() {
        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, displayDict.class);
                startActivity(intent);
            }
        });
    }

    public void viewSaved() {
        savedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, favDict.class);
                startActivity(intent);
            }
        });
    }
}
