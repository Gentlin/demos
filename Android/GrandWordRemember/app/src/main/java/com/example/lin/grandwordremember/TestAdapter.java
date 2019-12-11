package com.example.lin.grandwordremember;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import junit.framework.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAdapter extends BaseAdapter {
    private HashMap<Integer, String> result = new HashMap<>();
    private List<Map<String, String>> data ;
    private LayoutInflater mLayoutInflater ;
    private int[] choose;
    private Context context;
    public void setAns(int[] ans) {
        this.ans = ans;
    }

    private int[] ans;
    public void setSubmited(boolean submited) {
        isSubmited = submited;
    }

    public boolean isSubmited() {
        return isSubmited;
    }

    private boolean isSubmited = false;
    public class ViewHolder {
        TextView word;
        RadioGroup choices;
        RadioButton[] choice = new RadioButton[4];
    }

    public void setChoose(int[] choose) {
        this.choose = choose;
    }

    public int[] getChoose() {
        return choose;
    }
    private int textColor = Color.RED;
    public TestAdapter(Context context, List<Map<String, String>> data, int textColor) {
        this.data = data;
        mLayoutInflater = LayoutInflater.from(context);
        choose = new int[data.size()];
        this.context = context;
        this.textColor = textColor;
        for(int i = 0; i < data.size(); i++) {
            choose[i] = -1;
        }
    }

    public   HashMap<Integer, String> getResult() {
        return result;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Map<String, String> dataItem = data.get(position);
      //  if(null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.word = (TextView)convertView.findViewById(R.id.word);
            holder.word.setTextColor(textColor);
            holder.choices = (RadioGroup)convertView.findViewById(R.id.choices);
            holder.choice[0] = (RadioButton)convertView.findViewById(R.id.choice1);
            holder.choice[1] = (RadioButton)convertView.findViewById(R.id.choice2);
            holder.choice[2] = (RadioButton)convertView.findViewById(R.id.choice3);
            holder.choice[3] = (RadioButton)convertView.findViewById(R.id.choice4);
            convertView.setTag(holder);
            holder.word.setText(dataItem.get("word"));
            for(int i = 0; i < 4; i++) {
                holder.choice[i].setText(dataItem.get("choice"+(i+1)));;
            }
      //  } else {
      //      holder = (ViewHolder)convertView.getTag();
     //   }
        holder.choices.setOnCheckedChangeListener(null);
        holder.choices.clearCheck();
        if(isSubmited) {
         //   holder.choice[ans[position]].setChecked(true);
            holder.choice[ans[position]].setBackground(context.getResources()
                    .getDrawable(R.drawable.rectangle2, context.getTheme()));
            holder.choices.setClickable(false);
            for(int j = 0; j < 4;j ++)holder.choice[j].setClickable(false);
            return convertView;
        }

        if(choose[position]!=-1) {
            holder.choice[choose[position]].setChecked(true);
        }
        final int pos = position;
        holder.choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)group.findViewById(checkedId);
                int seq = 0;
                switch (checkedId) {
                    case R.id.choice1:seq = 0;break;
                    case R.id.choice2:seq = 1;break;
                    case R.id.choice3:seq = 2;break;
                    case R.id.choice4:seq = 3;break;
                }
                TestAdapter.this.choose[pos] = seq;
            }
        });
        return convertView;
    }
}
