package com.example.lin.grandwordremember;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudyStatDBOpenHandler extends SQLiteOpenHelper {
    public StudyStatDBOpenHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE words(_id integer primary key autoincrement," +
                "word varchar(64) unique," +
                "level int default 0, " +
                "test_count int default 0, " +
                "correct_count int default 0," +
                "last_test_time timestamp)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
