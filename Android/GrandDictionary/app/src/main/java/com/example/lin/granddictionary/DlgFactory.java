package com.example.lin.granddictionary;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DlgFactory {
    private static AlertDialog addWordDlg;
    private static AlertDialog changeWordDlg;
    static public AlertDialog produceChangeWordDlg(final MainActivity context, final int pos) {
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(context);
        changeWordDlg = dlgBuilder.setTitle("修改单词")
                .setIcon(R.drawable.dict)
                .setView(R.layout.change_word_dlg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String word = ((EditText)changeWordDlg.findViewById(R.id.new_word)).getText().toString();
                        String explanation = ((EditText)changeWordDlg.findViewById(R.id.explanation)).getText().toString();
                        String level = ((EditText)changeWordDlg.findViewById(R.id.level)).getText().toString();
                        String oldWord = context.getWordlist().get(pos).values().iterator().next();
                        ContentValues cv = new ContentValues();
                        cv.put("word",word);
                        cv.put("explanation", explanation);
                        cv.put("level", level);
                        SQLiteDatabase dbw = context.getDBHandler().getWritableDatabase();
                        dbw.update("dict", cv, "word='"+oldWord+"'", null);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("word", word);
                        map.put("explanation", explanation);
                        context.changeWordListItem(map, pos);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                return changeWordDlg;
    }
    static public AlertDialog produceDeleteWordDlg(final MainActivity context, final int pos) {
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(context);
        AlertDialog dlg = dlgBuilder.setTitle("删除单词")
                .setIcon(R.drawable.dict)
                .setMessage("你确定要删除这个单词吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String word = context.getWordlist().get(pos).values().iterator().next();
                        SQLiteDatabase db = context.getDBHandler().getWritableDatabase();
                        db.delete("dict","word='"+word+"'",null);
                        context.removeWordlistItem(pos);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

        return dlg;
    }
    static public AlertDialog produceAddWordDlg(final MainActivity context) {
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(context);
        addWordDlg = dlgBuilder.setTitle("添加新单词")
                .setIcon(R.drawable.dict)
                .setView(R.layout.add_word_dialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String word = ((EditText)addWordDlg.findViewById(R.id.new_word)).getText().toString();
                        String explanation = ((EditText)addWordDlg.findViewById(R.id.explanation)).getText().toString();
                        String level = ((EditText)addWordDlg.findViewById(R.id.level)).getText().toString();
                        boolean canOverride = ((CheckBox)addWordDlg.findViewById(R.id.override)).isChecked();
                        ContentValues cv = new ContentValues();
                        cv.put("word",word);
                        cv.put("explanation", explanation);
                        cv.put("level", level);
                        SQLiteDatabase db = context.getDBHandler().getReadableDatabase();
                        SQLiteDatabase dbw = context.getDBHandler().getWritableDatabase();
                        Cursor cursor = db.rawQuery("select word from dict where word='"+word+"'",null);
                        if(cursor != null && canOverride) {
                            dbw.update("dict", cv, "word='"+word+"'", null);
                        }else dbw.insert("dict",null, cv);
                        Log.d("添加单词",word);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("word", word);
                        context.addWordlistItem(map);
                        db.close();
                        dbw.close();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        return addWordDlg;
    }
}
