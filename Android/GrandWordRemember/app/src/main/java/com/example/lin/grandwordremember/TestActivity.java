package com.example.lin.grandwordremember;


import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.widget.Toast.LENGTH_LONG;

public class TestActivity extends AppCompatActivity {
    private ContentResolver mContentResolver;
    private Uri uri = Uri.parse("content://com.example.lin.words_provider/DictDB");
    private ArrayList<Word> mWords = new ArrayList<Word>();
    private ListView wordList;
    private Button submit;
    private Random mRandom = new Random();
    private StudyStatDBOpenHandler mStudyStatDBOpenHandler ;
    private TestAdapter mTestAdapter;
    private ArrayList<Map<String, String>> mData;
    private int numOfWord = 0;
    private boolean isSave = true;
    private int mColorOfWord;

    public TestActivity() {
        mData = new ArrayList<>();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("-快速记忆法");
        actionBar.setDisplayHomeAsUpEnabled(true);
        mContentResolver = getContentResolver();
        wordList = (ListView)findViewById(R.id.word_list);
        submit = (Button)findViewById(R.id.submit);
        mStudyStatDBOpenHandler = new StudyStatDBOpenHandler(this, "studystat.db",null,1);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        isSave = sp.getBoolean("save_checkbox_key",true);
        numOfWord = Integer.parseInt(sp.getString("num_of_word_edittext_key", "10"));
        Log.d("TestActivity获得设置数据","单词数量"+numOfWord+"颜色 "+sp.getString("word_color_list_key", "Red"));
        switch (sp.getString("word_color_list_key", "Red")) {
            case "Red":mColorOfWord = Color.RED;break;
            case "Green":mColorOfWord = Color.GREEN;break;
            case "Blue":mColorOfWord = Color.BLUE;break;
            case "Black":mColorOfWord = Color.BLACK;break;
        }
        beginTest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
                break;
                case R.id.begin_review:

                break;

                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void beginTest() {
        getWords();
        mData = new ArrayList<Map<String, String>>();
        for(int i = 0; i < numOfWord; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("word", mWords.get(i).getWord());
            ArrayList<Integer> rand = new ArrayList<Integer>(Arrays.asList(i, mRandom.nextInt(10),mRandom.nextInt(10)
                    ,mRandom.nextInt(10)));
            Collections.shuffle(rand);
            for(int j = 0; j < 4; j++) {
                item.put("choice"+ (j + 1), mWords.get(rand.get(j)).getExplanation());
            }
            mData.add(item);
        }
        mTestAdapter = new TestAdapter(this, mData, mColorOfWord);
        wordList.setAdapter(mTestAdapter);
    }
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    public void getWords() {
        SQLiteDatabase db = mStudyStatDBOpenHandler.getReadableDatabase();
        Cursor c = null;
        c = db.rawQuery("select word from words order by word desc",null);
        if(false == c.moveToNext()) c = mContentResolver.query(uri,new String[]{"word", "explanation", "level"},  null,null,"word limit 10");
        else {
            String w = c.getString(0);
            Log.d("最大字典单词",w);
            c = mContentResolver.query(uri,new String[]{"word", "explanation", "level"},  "word >'"+w+"'",null,"word limit "+numOfWord);
        }
        if(c == null) {
            Toast.makeText(TestActivity.this, "获取单词失败", LENGTH_LONG);
            Log.d("获得单词失败","");
            return;
        }
        mWords.clear();
        db.close();
        db = mStudyStatDBOpenHandler.getWritableDatabase();
        while(c.moveToNext()) {
            String w = c.getString(0);
            String e = c.getString(1);
            String l = c.getString(2);
            mWords.add(new Word(w, e,l));

            Log.d("获得单词", c.getString(0));
        }
    }
    public void OnSubmitBtnClick(View v) {
        Log.d("提交","");
        submit.setVisibility(View.INVISIBLE);
        SQLiteDatabase db = mStudyStatDBOpenHandler.getWritableDatabase();
        int[] ans = new int[mWords.size()];
        int[] choose = mTestAdapter.getChoose();
        for(int i = 0; i < mWords.size(); i++) {
            Word word = mWords.get(i);
            for(int j = 0; j < 4; j++) {
                if(mData.get(i).get("choice"+(j+1)).compareTo(word.getExplanation())==0){
                    ans[i] = j;
                    break;
                }
            }
            if(ans[i] == choose[i]) {
                db.execSQL("insert into words(word, level, test_count, correct_count, last_test_time) values(?,?,?,?,?)"
                        , new Object[]{word.getWord(), word.getLevel(), 1, 1, Util.getTime()});
                Log.d("回答","正确"+word.getWord());

            } else {
                db.execSQL("insert into words(word, level, test_count, correct_count, last_test_time) values(?,?,?,?,?)"
                        , new Object[]{word.getWord(), word.getLevel(), 1, 0, Util.getTime()});
                Log.d("回答",i+"错误");
            }
        }
        mTestAdapter.setSubmited(true);
        mTestAdapter.setAns(ans);
        wordList.setAdapter(mTestAdapter);
    }
    public TestActivity getTestActivityContext(){
        return this;
    }
}
