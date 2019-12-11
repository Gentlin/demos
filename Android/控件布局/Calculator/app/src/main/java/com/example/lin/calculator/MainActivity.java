package com.example.lin.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import java.util.*;
import java.text.DecimalFormat;
public class MainActivity extends AppCompatActivity {
    private Button[] button = new Button[20];
    private LinearLayout[] row = new LinearLayout[5];
    String[] btnText = new String[]{
            "CE", "C" , "←", "÷",
            "7" , "8" , "9" , "×",
            "4" , "5" , "6" ,"-" ,
            "1" , "2" , "3" ,  "+",
            "±" , "0" , "."  ,"="
    };
    private static String num = new String("");
    private static String toShow = new String("");
    private static Stack<Double> result = new Stack<Double>();
    private static Stack<String> opt = new Stack<String>();
    static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
    private TextView show;
    private LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = (LinearLayout)findViewById(R.id.root);
        show = findViewById(R.id.show);
        for(int i=0;i<5;i++){
            row[i] = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80*2);
            row[i].setOrientation(LinearLayout.HORIZONTAL);
            row[i].setLayoutParams(params);
            root.addView(row[i], params);
            LinearLayout.LayoutParams buttonlp = new LinearLayout.LayoutParams(70*2+10,LinearLayout.LayoutParams.MATCH_PARENT);
            for(int j=0;j<4;j++){
                final int index = 4*i + j;
                button[index] = new Button(MainActivity.this);
                button[index].setText(btnText[index]);
                button[index].setTextSize(40);
                buttonlp.setMargins(5, 5, 5,5);
                button[index].setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        MainActivity.push(button[index].getText().toString());
                        if(toShow.length()>10)toShow = toShow.substring(0,10);
                        show.setText(toShow);
                    }
                });

                row[i].addView(button[index], buttonlp);
            }
        }
    }
    public static void push(String str){
        if(str.equals("C")){
            toShow = "";
            num = "";
            result.clear();
            opt.clear();
        }
        else if(str.equals("CE")){
            toShow = "";
            num = "";
        }
        else if(str.equals("←")){
            if(num.length() !=0){
                num = num.substring(0, num.length()-1);
            }
            toShow = num;
        }
        else if(str.equals("±")){
            Double num1 = Double.parseDouble(num);
            num1 = -num1;
            num = num1.toString();
            toShow = num;
        }
        else if(str.equals("+") || str.equals("-") || str.equals("×") || str.equals("÷") || str.equals("=")){

            if(opt.size() == 0 && !num.equals("")){
                result.push(Double.parseDouble(num));
                num = "";
                opt.push(str);
            }
            else if(opt.size()==1 && num.equals("")){
                opt.pop();
                opt.push(str);
            }
            else if(opt.size()==1 && !num.equals("")) {
                Double num1 = result.pop();
                Double num2 = Double.parseDouble(num);
                String operator = opt.pop();
                Double num3 = 0d ;
                switch (operator){
                    case "+": num3 = num1 + num2;break;
                    case "-": num3 = num1 - num2;break;
                    case "×":num3 = num2 * num1;break;
                    case "÷":num3 = num1 / num2;break;
                    default:
                        break;
                }
                if(!str.equals("=")){
                    opt.push(str);
                }
                toShow = (decimalFormat.format(num3)).toString();
                num = toShow;
                result.push(num3);
            }
        }
        else if(str.equals(".")){
            if(num.indexOf(str)==-1 && !num.equals("")){
                num += str;
                toShow = num;
            }
        }
        else {
            num += str;
            toShow = num;
        }
    }
}
