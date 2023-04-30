package com.dalae37.android.dl_log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DateDB extends SQLiteOpenHelper{
    static final String DB_NAME = "date_db";
    static int DB_VERSION = 1;
    static String TABLE_NAME = "date_tb";
    public DateDB(Context context){
        super(context, DB_NAME, null ,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_sql = "create table " + TABLE_NAME + "(_id integer primary key autoincrement, year text, month text, day text, gameid text, nickname text);";
        try{
            db.execSQL(create_sql);
        }
        catch(Exception e){
            Log.e("MemberDB", "DB Create Error");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dic");
        onCreate(db);
    }
}
