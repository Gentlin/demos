package com.example.lin.chronometer;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
public class MainActivity extends AppCompatActivity {
    private boolean isStart;
    private boolean isFirstStart;
    private Button start;
    private Button pause;
    private Button reset;
    private Chronometer startTime;
    private Chronometer elapse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        pause.setEnabled(false);
        Button reset = findViewById(R.id.reset);
        startTime = findViewById(R.id.startTime);
        elapse = findViewById(R.id.elapse);
        startTime.setFormat("%s");
        startTime.setBase(SystemClock.elapsedRealtime());
        startTime.start();
        elapse.setFormat("%s");
        isStart = false;
        isFirstStart = true;
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!isStart){
                    if(isFirstStart){
                        isFirstStart = false;
                        elapse.setBase(SystemClock.elapsedRealtime());
                    }
                    isStart = true;
                    start.setEnabled(false);
                    pause.setEnabled(true);
                    elapse.setVisibility(View.VISIBLE);
                    elapse.start();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isStart){
                    isStart = false;
                    start.setEnabled(true);
                    pause.setEnabled(false);
                    elapse.stop();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                elapse.setBase(SystemClock.elapsedRealtime());
            }
        });

        elapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapse.setVisibility(View.INVISIBLE);
            }
        });
    }
}
