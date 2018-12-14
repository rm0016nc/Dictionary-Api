package com.example.rm0016nc.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyDictionaryRequest extends AsyncTask<String, Integer, String> implements View.OnClickListener {

    final String app_id = "ea0004c2";
    final String app_key = "5fa9c407fef62b5419284d9c02825ed1";
    String myurl;
    Context context;
    databaseHelper myDb;
    database mydb;
    TextView t1;
    EditText e1;
    String word;
    Button favBtn;
    String def;

    MyDictionaryRequest(Context context, TextView t1, EditText e1, Button favBtn, String def){
        this.context = context;
        this.t1 = t1;
        this.e1 = e1;
        this.favBtn = favBtn;
        this.def = def;
    }

    public MyDictionaryRequest(String def) {
        this.def = def;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        this.favBtn = (Button) rootView.findViewById(R.id.favBtn);
        this.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database helper = new database(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                final String word  = e1.getText().toString();
                final String output = word.substring(0,1).toUpperCase() + word.substring(1);

                helper.addData(output, def);
                Toast.makeText(context,"Data added",Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }


    @Override
    protected String doInBackground(String... params) {
        myurl = params[0];
        try {
            URL url = new URL(myurl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute( String s) {
        super.onPostExecute(s);

        try
        {
            JSONObject js = new JSONObject(s);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

            JSONObject d = sensesArray.getJSONObject(0);
            JSONArray de = d.getJSONArray("definitions");

            databaseHelper dHelper = new databaseHelper(context);
            SQLiteDatabase myDb = dHelper.getWritableDatabase();

            database helper = new database(context);
            SQLiteDatabase db = helper.getWritableDatabase();

            def = de.getString(0);

            final String word  = e1.getText().toString();
            final String output = word.substring(0,1).toUpperCase() + word.substring(1);
            final String definition = def.substring(0,1).toUpperCase() + def.substring(1);

            t1.setText( "Definition of " +output + " :" + "\n" + "\n" + definition );
//            Toast.makeText(context,def,Toast.LENGTH_SHORT).show();
            dHelper.insertData(output, def);

        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database helper = new database(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                final String word  = e1.getText().toString();
                if (word.matches("")) {
                    Toast.makeText(context,"Please input word in the textbox",Toast.LENGTH_SHORT).show();
                } else {
                    final String output = word.substring(0,1).toUpperCase() + word.substring(1);

                    helper.addData(output, def);
                    Toast.makeText(context,"Word saved",Toast.LENGTH_SHORT).show();
                    e1.getText().clear();
                }
            }
        });
    }


    public String getDef() {
        return def;
    }

    @Override
    public void onClick( View v) {
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database helper = new database(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                final String word  = e1.getText().toString();
                final String output = word.substring(0,1).toUpperCase() + word.substring(1);

                helper.addData(output, def);
                Toast.makeText(context,"Data added",Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void favList (View v) {
////        database helper = new database(context);
////        SQLiteDatabase db = helper.getWritableDatabase();
////
////        final String word  = e1.getText().toString();
////        final String output = word.substring(0,1).toUpperCase() + word.substring(1);
////
////        helper.addData(output, def);
////    }

//    public void AddData (final String aWord, final String aDef) {
////        boolean isInserted =
//            myDb.insertData(aWord, aDef);
////        if (isInserted = true)
////            Toast.makeText(context, "Data inserted", Toast.LENGTH_LONG).show();
////        else
////            Toast.makeText(context, "Data not inserted", Toast.LENGTH_LONG).show();
//    }


}
