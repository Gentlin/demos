package com.example.lin.grandwordremember;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private EditTextPreference mEtPreference;
    private ListPreference mListPreference;
    private CheckBoxPreference mCheckPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        initPreferences();
        Bundle bundle=this.getArguments();
        String content = bundle.getString("setting");
        Log.d("测试setting",content);

    }

    private void initPreferences() {
        mEtPreference = (EditTextPreference)findPreference("num_of_word_edittext_key");
        mListPreference = (ListPreference)findPreference("word_color_list_key");
        mCheckPreference = (CheckBoxPreference)findPreference("save_checkbox_key");

        mListPreference.setTitle("选择单词颜色");
        mEtPreference.setTitle("设置单词数量");
        mCheckPreference.setTitle("是否在保存时进行统计");
    }

    @Override
    public void onResume() {
        super.onResume();

        // Setup the initial values
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        mListPreference.setSummary(sharedPreferences.getString("word_color_list_key", ""));
        mEtPreference.setSummary(sharedPreferences.getString("num_of_word_edittext_key", "10"));
        mCheckPreference.setSummary(sharedPreferences.getBoolean("save_checkbox_key",true) ? "是" : "否");
        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("num_of_word_edittext_key")) {
            mEtPreference.setSummary(
                    sharedPreferences.getString(key, "20"));
        } else if(key.equals("word_color_list_key")) {
            mListPreference.setSummary(sharedPreferences.getString(key, ""));
        } else if(key.equals("save_checkbox_key")) {
            mCheckPreference.setSummary(sharedPreferences.getBoolean(key,true) ? "是" : "否");
        }
    }
}

