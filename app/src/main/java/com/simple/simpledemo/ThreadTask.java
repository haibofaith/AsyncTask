package com.simple.simpledemo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by user on 2016/12/21.
 */

public abstract class ThreadTask<T> extends Thread{

    private Handler handler;

    public ThreadTask(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                onResult((T)msg.obj);
            }
        };
        Log.d("InternalHandler",handler.toString());
    }

    @Override
    public void run() {
        super.run();
        Message message = Message.obtain();
        message.obj = onOoInBackground();
        handler.sendMessage(message);
    }
    /**
     * 任务开始之前
     * */
    public abstract void onStart();
    /**
     * 子线程中调用，运行在子线程
     * */
    public abstract T onOoInBackground();
    /**
     * 子线程返回的结果，运行在主线程
     * */
    public abstract void onResult(T t);

    public void execute(){
        onStart();
        start();
    }
}
