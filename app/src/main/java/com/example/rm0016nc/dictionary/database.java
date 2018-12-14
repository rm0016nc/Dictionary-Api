package com.example.rm0016nc.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favwords.db";
    public static final String TABLE_NAME = "favwords_table";
    public static final String COL_1 = "ID";
    public static final String COL2 = "WORD";
    public static final String COL3 = "DEF";

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME,  null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, WORD STRING, DEF STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String word, String def) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, word);
        contentValues.put(COL3, def);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Integer resetData() {
        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME,"ID = ?" ,new String[] {id});
        return db.delete(TABLE_NAME,null,null);
    }
}
