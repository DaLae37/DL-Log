package com.dalae37.android.dl_log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemberDB extends SQLiteOpenHelper {
    static final String DB_NAME = "member.db";
    static int DB_VERSION = 1;
    static String TABLE_NAME = "member_tb";
    public MemberDB(Context context){
        super(context, DB_NAME, null ,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_sql = "create table " + TABLE_NAME + "(_id integer primary key autoincrement, id text, pw text, nickname text);";
        try{
            db.execSQL(create_sql);
        }
        catch(Exception e){
            Log.e("MemberDB", "DB Create Error");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
