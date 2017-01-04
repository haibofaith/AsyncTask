package com.simple.simpledemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main2Activity extends Activity {
    private static final String TAG = "Main2Activity";
    private ProgressDialog mDialog;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mTextView = (TextView) findViewById(R.id.id_tv);

        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);

        new MyAsyncTask().execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected void onPreExecute()
        {
            mDialog.show();
            Log.e(TAG, Thread.currentThread().getName() + " onPreExecute ");
        }

        @Override
        protected Void doInBackground(Void... params)
        {

            // 模拟数据的加载,耗时的任务
            for (int i = 0; i < 100; i++)
            {
                try
                {
                    Thread.sleep(80);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            Log.e(TAG, Thread.currentThread().getName() + " doInBackground ");
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            mDialog.setProgress(values[0]);
            Log.e(TAG, Thread.currentThread().getName() + " onProgressUpdate ");
        }

        @Override
        protected void onPostExecute(Void result)
        {
            // 进行数据加载完成后的UI操作
            mDialog.dismiss();
            mTextView.setText("LOAD DATA SUCCESS ");
            Log.e(TAG, Thread.currentThread().getName() + " onPostExecute ");
        }
    }
}
