package com.example.rm0016nc.dictionary;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TwoColumns_ListAdapter extends ArrayAdapter<Dicts> {

    private LayoutInflater mInflater;
    private ArrayList<Dicts> dict;
    private int aViewResourceId;

    public TwoColumns_ListAdapter(Context context, int textViewResourceId, ArrayList<Dicts> dict) {
        super(context, textViewResourceId, dict);
        this.dict = dict;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aViewResourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(aViewResourceId,null);

        Dicts dicts = dict.get(position);

        if(dicts != null) {
            TextView words = (TextView) convertView.findViewById(R.id.word);
            TextView wdefs = (TextView) convertView.findViewById(R.id.definition);

            if (words != null) {
                words.setText(dicts.getWords());
            }

            if (wdefs != null) {
                wdefs.setText(dicts.getDefs());
            }
        }
        return convertView;
    }
}
