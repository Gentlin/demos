package com.example.lin.grandwordremember;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

public class NewPreferenceActivity extends AppCompatActivity{
    public NewPreferenceActivity getContext() {
        return this;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_perference);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("-系统设置");
        actionBar.setDisplayHomeAsUpEnabled(true);


        Bundle bundle;
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        SettingFragment fg = new SettingFragment();
        bundle = new Bundle();
        bundle.putString("setting", "设置");
        fg.setArguments(bundle);
        fTransaction.add(R.id.ly_content,fg);
        fTransaction.commit();
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
