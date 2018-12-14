package com.example.rm0016nc.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Two_column_listAdapter  extends ArrayAdapter<Favs> {

    private LayoutInflater mInflater;
    private ArrayList<Favs> favs;
    private int aViewResourceId;

    public Two_column_listAdapter(Context context, int textViewResourceId, ArrayList<Favs> favs) {
        super(context, textViewResourceId, favs);
        this.favs = favs;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aViewResourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(aViewResourceId,null);

        Favs fav = favs.get(position);

        if(fav != null) {
            TextView words = (TextView) convertView.findViewById(R.id.favWord);
            TextView wdefs = (TextView) convertView.findViewById(R.id.favDef);

            if (words != null) {
                words.setText(fav.getWords());
            }

            if (wdefs != null) {
                wdefs.setText(fav.getDefs());
            }
        }
        return convertView;
    }
}
