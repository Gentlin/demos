package com.example.lin.gridviewsimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Map<String, Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = findViewById(R.id.gridView);
        list = new ArrayList<Map<String, Object>>();
        int[] images = { R.drawable.cwt, R.drawable.dlrb,
                R.drawable.lh, R.drawable.wjk, R.drawable.ym,
                R.drawable.zly, R.drawable.zyx};
        String[] names = { "陈伟霆", "迪丽热巴", "鹿晗",
                "王俊凯", "杨幂", "赵丽颖", "张艺兴" };
        int[] ages = { 18, 21, 22, 32, 31, 18, 20, 25 };
        for(int i=0;i<images.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", images[i]);
            map.put("name", names[i]);a
            map.put("age", ages[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.grid_item, new String[]{"icon", "name", "age"},
            new int[]{R.id.icon, R.id.name, R.id.age});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Short Click: "+list.get(position).get("name").toString(),Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Long Click: "+list.get(position).get("name").toString(),Toast.LENGTH_SHORT).show();
                return true;  //true: 只执行长按事件(ShortClick事件失效)
            }
        });
    }
}
