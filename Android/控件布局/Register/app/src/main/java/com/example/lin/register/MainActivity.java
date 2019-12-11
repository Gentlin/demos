package com.example.lin.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.graphics.Color;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.register);
        final TextView userName = findViewById(R.id.userName);
        final TextView pwd = findViewById(R.id.pwd);
        final CheckBox[] chk = new CheckBox[3];
        chk[0] = findViewById(R.id.checkbox1);
        chk[1] = findViewById(R.id.checkbox2);
        chk[2] = findViewById(R.id.checkbox3);
        final RadioGroup grade = findViewById(R.id.grade);
        final Spinner spinner = findViewById(R.id.spiner1);
        final String[] colleges = getResources().getStringArray(R.array.colleges);
        spinner.setAdapter(new MyArrayAdapter(MainActivity.this, colleges));
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                StringBuffer info = new StringBuffer();
                info.append("用户名：");
                if(userName.getText().length() != 0){
                    info.append(userName.getText());
                }
                info.append("\n");
                info.append("密码：");
                if(pwd.getText().length() != 0){
                    info.append(pwd.getText());
                }
                info.append("\n");
                info.append("爱好：");
                boolean first = true;
                for(CheckBox c:chk){
                    if(c.isChecked()){
                        if(first){
                            info.append(c.getText());
                            first = false;
                        }
                        else {
                            info.append("、"+c.getText());
                        }
                    }
                }
                info.append("\n");
                info.append("学院：");
                info.append(spinner.getSelectedItem());
                info.append("\n");
                info.append("年级：");
                int id = grade.getCheckedRadioButtonId();
                if(id != -1) {
                    info.append(((RadioButton)findViewById(id)).getText());
                }
                info.append("\n");
                Switch switch1 = findViewById(R.id.switch1);
                info.append("全日制：");
                if(switch1.isChecked())info.append("是");
                else info.append("否");

                Toast toast = Toast.makeText(MainActivity.this, info, Toast.LENGTH_LONG);
                int toastTextViewid = getResources().getSystem().getIdentifier("message", "id", "android");
                TextView toastTextView = (toast.getView().findViewById(toastTextViewid));
                toastTextView.setGravity(Gravity.LEFT);

                toastTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT));

                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,180);
                toast.show();
            }
        });
    }
}

class MyArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private String [] stringArray;
    public MyArrayAdapter(Context context, String[] stringArray) {
        super(context, android.R.layout.simple_spinner_item, stringArray);
        this.context = context;
        this.stringArray = stringArray;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent,false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(stringArray[position]);
        tv.setTextSize(18f);
        return convertView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        };
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(stringArray[position]);
        tv.setTextSize(18f);
        return convertView;
    }
}
