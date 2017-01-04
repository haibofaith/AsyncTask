package com.simple.simpledemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by user on 2016/12/21.
 */

public abstract class ThreadTask3<T>{

    private Handler handler;
    //定义一个定长线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public ThreadTask3(){
        handler = InternalHandler.getHandler();
    }

    public void run() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = new ResultData<>(ThreadTask3.this,onOoInBackground());
                handler.sendMessage(message);
            }
        });
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
        run();
    }

    private static class InternalHandler extends Handler {
        private static InternalHandler handler;

        private InternalHandler() {
            super(Looper.getMainLooper());
        }

        public static InternalHandler getHandler(){
            if (handler==null){
                synchronized (InternalHandler.class){
                    if (handler==null){
                    handler = new InternalHandler();
                    }
                }
            }
            Log.d("InternalHandler",handler.toString());
            return handler;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ResultData resultData = (ResultData) msg.obj;
            resultData.task2.onResult(resultData.data);
        }
    }

    private static class ResultData<T>{
        ThreadTask3 task2;
        T data;

        public ResultData(ThreadTask3 task2, T data) {
            this.task2 = task2;
            this.data = data;
        }
    }
}
