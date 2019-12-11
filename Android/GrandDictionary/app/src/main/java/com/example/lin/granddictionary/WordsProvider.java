package com.example.lin.granddictionary;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class WordsProvider extends ContentProvider {
    private Dictdb dbHandler;
    private static UriMatcher mUriMatcher;
    private static final String AUTHORITY = "com.example.lin.words_provider";
    private static final int MATCH_DICT_DB = 0;
    @Override
    public boolean onCreate() {
        dbHandler  = new Dictdb(getContext(), "dictDB", null, 1);
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "DictDB", MATCH_DICT_DB);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = null;
        switch (mUriMatcher.match(uri)) {
            case MATCH_DICT_DB:
                 cursor = db.query("dict", projection, selection, selectionArgs, "", "",sortOrder);
                break;
                default:
                   throw new IllegalArgumentException("unknown uri" + uri);

        }
      //  db.close();
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        switch (mUriMatcher.match(uri)){
            case MATCH_DICT_DB:
                 db.insert("dict",null,values);
                break;
                default:
                    throw new IllegalArgumentException("invalid "+uri);
        }
        db.close();
        return null;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        switch(mUriMatcher.match(uri)) {
            case MATCH_DICT_DB:
                db.update("dict", values,selection,selectionArgs);
                break;
            default:
                    throw  new IllegalArgumentException("invalid uri" + uri);
        }
        return 0;
    }
}