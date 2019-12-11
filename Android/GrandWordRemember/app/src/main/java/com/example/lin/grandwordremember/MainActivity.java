package com.example.lin.grandwordremember;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private Uri uri = Uri.parse("content://com.example.lin.words_provider/DictDB");
    private ContentResolver mContentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("学霸背单词");
        getSupportActionBar().setSubtitle("-快速记忆法");
        mContentResolver = getContentResolver();
        dlgBuilder = new AlertDialog.Builder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.begin_test:
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.add_word:
                addWord();
                break;
            case R.id.study_statictis:
                Intent intent1 = new Intent(this, StudyStatActivity.class);
                startActivity(intent1);
                break;
            case R.id.search_word:
                break;
            case R.id.setting:
                Intent intent2 = new Intent(this, NewPreferenceActivity.class);
                startActivity(intent2);
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
    private AlertDialog addWordDlg;
    private  AlertDialog.Builder dlgBuilder;
    public void addWord() {
        addWordDlg = dlgBuilder.setTitle("添加新单词")
                .setIcon(R.drawable.dict)
                .setView((TableLayout) getLayoutInflater()
                        .inflate(R.layout.add_word_dialog, null))
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
                        Cursor cursor = mContentResolver.query(uri,new String[]{"word"}, "word='"+word+"'",null,"" );
                        if(cursor != null && canOverride) {
                            mContentResolver.update(uri,  cv, "word='"+word+"'", null);
                        }else mContentResolver.insert(uri, cv);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

        addWordDlg.show();
    }
}
