package com.example.lin.granddictionary;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    private ListView mListView ;
    private ArrayList<Map<String, String>> wordList;
    private SimpleAdapter simpleAdapter;
    private TextView mExplanationTextView;
    private LinearLayout mAlphaLinearLayout;
    private HorizontalScrollView mHorizontalScrollView;

    private TextView[] textView = new TextView[26];
    private int seqOfTextViewChoosed = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("简易英文词典");
        getSupportActionBar().setSubtitle("中山大学");
        mListView = (ListView)findViewById(R.id.listView);
        mExplanationTextView = (TextView)findViewById(R.id.show_word_view);
        mHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.alpha_scrollView);
        mAlphaLinearLayout = (LinearLayout)findViewById(R.id.alpha_linear_layout);
        mDlgBuilder = new AlertDialog.Builder(this);

        for(int i = 0; i < 26; i++) {
            textView[i] = new TextView(MainActivity.this);
            textView[i].setText(String.valueOf((char)('a'+i)));
            textView[i].setTextSize(12);
            final int index = i;
            textView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(seqOfTextViewChoosed != -1) {
                        textView[seqOfTextViewChoosed].setBackgroundColor(Color.argb(255,200,200,255));
                    }
                    seqOfTextViewChoosed = index;
                    textView[seqOfTextViewChoosed].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    SQLiteDatabase db = mDbHandler.getReadableDatabase();
                    Cursor cursor = db.query("dict", new String[]{"word"}, "word like '"
                            +String.valueOf((char)('a'+index))+"%'", null, null, null,null);
                    wordList.clear();
                    while(cursor.moveToNext()) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("word", cursor.getString(0));
                        wordList.add(map);
                    }
                    updateListView(wordList);
                    db.close();
                }
            });
            textView[i].setBackgroundColor(Color.argb(255,200,200,255));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(30,
                    30);
            textView[i].setGravity(Gravity.CENTER);
            lp.setMargins(5,5,5,5);
            mAlphaLinearLayout.addView(textView[i], lp);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db = mDbHandler.getReadableDatabase();
                String word=wordList.get(position).get("word");
                Cursor cursor = db.query("dict",new String[]{"explanation"},"word='"
                        +word+"'",null, null, null, null);
                if(cursor == null) {
                    Log.d("找不到点击单词的解释",word);
                }
                cursor.moveToNext();
                String explanation = cursor.getString(0);

                mExplanationTextView.setText(word+"\n"+explanation);
                db.close();
            }

        });
       mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
               popupMenu.getMenuInflater().inflate(R.menu.list_view_item_long_click, popupMenu.getMenu());
               final int pos = position;
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()) {
                           case R.id.change:
                               AlertDialog changeWordDlg = DlgFactory.produceChangeWordDlg(MainActivity.this,
                                       pos);
                               changeWordDlg.show();
                               break;
                           case R.id.delete:
                                AlertDialog deleteWordDlg = DlgFactory.produceDeleteWordDlg(MainActivity.this, pos);
                                deleteWordDlg.show();
                               break;
                       }
                       return true;
                   }
               });
               popupMenu.show();
               return true;
           }
       });
        wordList = new ArrayList<Map<String, String>>();
        getWordList();
    }
    public void changeWordListItem(Map<String, String> map, int pos){
        wordList.set(pos, map);
        updateListView(MainActivity.this.wordList);
    }
    public void removeWordlistItem(int pos){
        wordList.remove(pos);
        updateListView(wordList);
    }
    public SQLiteOpenHelper getDBHandler() {
        return mDbHandler;
    }
    public ArrayList<Map<String, String>> getWordlist() {
        return wordList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private AlertDialog addWordDlg;
    private boolean isShowMean = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                search();
                break;
            case R.id.add:
               add();
                break;
            case R.id.download:
                downloadWord();
                break;
            case R.id.show_mean:
                isShowMean = !isShowMean;
                item.setChecked(isShowMean);
                mExplanationTextView.setVisibility(isShowMean ? View.INVISIBLE : View.VISIBLE);
                updateListView(wordList);
                break;
             default:
                 return super.onOptionsItemSelected(item)  ;
        }
        return true;
    }

    private void add() {
        if(null == addWordDlg) {
            addWordDlg = DlgFactory.produceAddWordDlg(this);
        }
        addWordDlg.show();
    }
    public void addWordlistItem(Map<String, String> map) {
        wordList.add(map);
        updateListView(wordList);
    }
    private AlertDialog.Builder mDlgBuilder;
    private AlertDialog searchDlg;
    private void search() {
        if(null == searchDlg) {
            searchDlg = mDlgBuilder.setIcon(R.drawable.dict)
            .setTitle("查找单词")
            .setView((TableLayout)getLayoutInflater().inflate(R.layout.search_dlg,null))
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = mDbHandler.getReadableDatabase();
                            String w = ((EditText)searchDlg.findViewById(R.id.the_word_to_search)).getText().toString();
                            Cursor cursor = db.rawQuery("select explanation from dict where word='"+w+"'",null);

                            if(false == cursor.moveToNext()) {
                                Toast.makeText(MainActivity.this, "找不到单词"+w, Toast.LENGTH_LONG);
                                return;
                            }

                            String explanation = cursor.getString(0);
                            mExplanationTextView.setText(w+"\n"+explanation);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
        }
        searchDlg.show();
    }
    final static int LIST_VIEW_UPDATE = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST_VIEW_UPDATE:
                    wordList.clear();
                    getWordList();
                    break;
                    default:
                        break;
                case DOWNLOAD_PROGRESS_CHANGE:
                    Log.d("下载进程改变",String.valueOf(100 * downloadWordNum / numOfWord) );
                    if(downloadWordNum < numOfWord) pd.setProgress(100 * downloadWordNum / numOfWord);
                    else pd.dismiss();
                    break;
                case GET_TOTAL_NUM:
                    pd.setMax(100);
                    break;
            }
        }
    };
    ProgressDialog pd;
    private Dictdb mDbHandler = new Dictdb(MainActivity.this, "dictDB", null, 1);

    public void getWordList() {
        Log.d("getWordList", "");
        SQLiteDatabase db = mDbHandler.getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery("select word, explanation from dict", null);
        while(cursor.moveToNext()) {
            String w = cursor.getString(0);
            Log.d("显示单词", w);
            HashMap<String, String> map = new HashMap<>();
            map.put("explanation", cursor.getString(1));
            map.put("word", w);
            wordList.add(map);
        }
        Collections.sort(wordList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                 String value1 = o1.values().iterator().next();
                String value2 = o2.values().iterator().next();
                 return (value1.toLowerCase()).compareTo(value2.toLowerCase());
            }
        });
        db.endTransaction();
        updateListView(wordList);
        //simpleAdapter.notifyDataSetChanged();
        db.close();
    }
    private void updateListView(ArrayList<Map<String, String>> wordList) {
        if(!isShowMean)simpleAdapter = new SimpleAdapter(this, wordList, R.layout.word_list_item, new String[]{"word"},
                new int[]{R.id.word_item});
        else {
            simpleAdapter = new SimpleAdapter(this, wordList, R.layout.word_list_item, new String[]{"word","explanation"},
                    new int[]{R.id.word_item,R.id.explanation_item});

        }
        mListView.setAdapter(simpleAdapter);
    }
    private int numOfWord = 8473;
    private final int DOWNLOAD_PROGRESS_CHANGE = 2;
    private final int GET_TOTAL_NUM = 3;
    private int downloadWordNum = 0;
    public void downloadWord() {
        pd = new ProgressDialog(this);
        pd.setTitle("下载单词");
        pd.setMessage("下载完成任务比");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(false);
        pd.show();
        new Thread() {
            @Override
            public void run(){
                SQLiteDatabase db = mDbHandler.getWritableDatabase();
                String w = "";
                try{
                    URL url = new URL("http://172.18.187.233:8080/testjsp/temp/dict123456.jsp");
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    int status = urlConnection.getResponseCode();
                    if(status != 200) {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "下载失败"+status, LENGTH_LONG);
                        Looper.loop();
                    }
                    String text = getInputStreamText(urlConnection.getInputStream());
                    JSONArray words = new JSONArray(text);
                    numOfWord = words.length();
                    mHandler.sendEmptyMessage(GET_TOTAL_NUM);
                    db.beginTransaction();
                    for(int i = 0; i < words.length() - 1; i++) {
                        JSONObject word = words.getJSONObject(i);
                        w = word.getString("word");
                        ContentValues cv = new ContentValues();
                        cv.put("word", w);
                        cv.put("explanation",word.getString("explanation"));
                        cv.put("level", word.getString("level"));
                        cv.put("modified_time", getTime());
                       long l = db.insert("dict", null,cv );
                       Log.d("插入单词", l + w);
                        if(l == -1) {
                             Log.d("插入单词失败", w);
                         }
                        if(i % 1000 == 0) {
                            mHandler.sendEmptyMessage(DOWNLOAD_PROGRESS_CHANGE);
                            downloadWordNum = i;
                        }
                         //  db.execSQL("INSERT INTO dict(word, explanation,level,modified_time) values(?,?,?,?)", new Object[]{w,
                      //  word.getString("explanation"), word.getString("level"), getTime()});
                    }
                    downloadWordNum = words.length();
                    mHandler.sendEmptyMessage(DOWNLOAD_PROGRESS_CHANGE);
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                   Log.d("下载单词失败，可能已下载", w);
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                    db.close();
                    Log.d("下载完成","");
                    mHandler.sendEmptyMessage(LIST_VIEW_UPDATE);
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "下载完成", LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }.start();
    }
    static public String getTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + second;
    }
    static String getInputStreamText(InputStream is) {
        InputStreamReader isr = null;
        StringBuilder text = null;
        try {
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader bufReader = new BufferedReader(isr);
            text = new StringBuilder();
            String line;
            while((line = bufReader.readLine()) != null) {
                text.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }

}
