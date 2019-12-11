package com.example.lin.grandwordremember;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudyStatActivity extends AppCompatActivity {
    private ListView mListViewOfStudyStat;
    private ArrayList<Map<String,String>> mData;
    private StudyStatDBOpenHandler mStudyStatDBOpenHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studystat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("-测试统计");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mListViewOfStudyStat = (ListView)findViewById(R.id.study_stat_list_view);
        mData = new ArrayList<Map<String, String>>();
        mStudyStatDBOpenHandler = new StudyStatDBOpenHandler(this, "studystat.db",null,1);

        SQLiteDatabase db = mStudyStatDBOpenHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select word, level, test_count, correct_count from words",null);
        HashMap<String, String> map = new HashMap<String, String>() ;
        map.put("word", "单词");
        map.put("level","级别");
        map.put("test", "测试");
        map.put("correct", "正确");
       mData.add(map);
        while(cursor.moveToNext()) {
            map = new HashMap<String, String>();
            Log.d("单词统计", cursor.getString(0));
            map.put("word", cursor.getString(0));
            map.put("level",cursor.getString(1));
            map.put("test", cursor.getString(2));
            map.put("correct", cursor.getString(3));
            mData.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, mData, R.layout.study_stat_listview_item,
                new String[]{"word", "level","test", "correct"},
                new int[]{R.id.word, R.id.level, R.id.test, R.id.correct});
        mListViewOfStudyStat.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

