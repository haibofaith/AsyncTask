package com.simple.simpledemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextView my_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my_text = (TextView)findViewById(R.id.my_text);
    }

    public void Func(View view){
        for (int i = 0; i < 30; i++) {
            final int index = i;
            new ThreadTask3<String>() {
                @Override
                public void onStart() {
                    Log.d("ThreadTask", "我是加载动画");
                }

                @Override
                public String onOoInBackground() {
                    Log.e("ThreadTask", "测试日志" + index);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("ThreadTask", "我是执行过程");
                    return "结果返回";
                }

                @Override
                public void onResult(String s) {
                    Log.d("ThreadTask", "结果" + s);
                    my_text.setText("结果" + s);
                }
            }.execute();
        }
    }

    /**public void Func(View view){
        new ThreadTask<String>(){
            @Override
            public void onStart() {
                Log.d("ThreadTask","我是加载动画");
            }

            @Override
            public String onOoInBackground() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "结果返回";
            }

            @Override
            public void onResult(String s) {
                Log.d("ThreadTask","结果"+s);
                my_text.setText("结果"+s);
            }
        }.execute();
    }*/

    public void Func2(View view){
        startActivity(new Intent(this,Main2Activity.class));
    }
}
