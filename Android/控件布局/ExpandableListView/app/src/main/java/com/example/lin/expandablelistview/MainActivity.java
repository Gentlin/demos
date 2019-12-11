package com.example.lin.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    public String[] groupStrings = {"1.定义一个ExpandableListView","2.定义分组布局", "3.定义分组子选项布局", "4.定义适配器", "5.选用适配器"};
    public String[][] childStrings = {
            {"配置常用属性的值"},
            {"常用的有线性布局"},
            {"子选项可以是TextView，imageV等控件"},
            {"1.扩展ExpandableListAdapter", "2.使用SimpleExpandableListAdapter将两个list打包", "3.使用SimpleCursorTreeAdapter将Cursor数据打包成SimpleCursorTreeAdapter"},
            {"调用ExpandableListView对象的setAdapter方法"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return groupStrings.length;
            }
            //        获取指定分组中的子选项的个数
            @Override
            public int getChildrenCount(int groupPosition) {
                return childStrings[groupPosition].length;
            }
            //        获取指定的分组数据
            @Override
            public Object getGroup(int groupPosition) {
                return groupStrings[groupPosition];
            }

            //   获取指定分组中的指定子选项数据
            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return childStrings[groupPosition][childPosition];
            }
            //   获取指定分组的ID, 这个ID必须是唯一的
            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }
            //        获取子选项的ID, 这个ID必须是唯一的
            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }
            //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
            @Override
            public boolean hasStableIds() {
                return true;
            }
            private TextView getTextView(){
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(MainActivity.this);
                textView.setPadding(50, 0 , 0,0);
                textView.setLayoutParams(lp);
                return textView;
            }
            //        获取显示指定分组的视图
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                TextView groupView = getTextView();
                groupView.setText(groupStrings[groupPosition]);
                groupView.setTextSize(20);
                return groupView;
            }
            //        获取显示指定分组中的指定子选项的视图
            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView childView = getTextView();
                childView.setText(childStrings[groupPosition][childPosition]);
                return childView;
            }
            //     指定位置上的子元素是否可选中
            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };

        ExpandableListView expandableListView = findViewById(R.id.expand_list);
        expandableListView.setAdapter(adapter);
    }

}



