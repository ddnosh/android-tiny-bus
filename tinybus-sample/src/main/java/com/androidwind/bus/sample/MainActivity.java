package com.androidwind.bus.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidwind.bus.Subscriber;
import com.androidwind.bus.TestBackgroundEvent;
import com.androidwind.bus.TestEvent;
import com.androidwind.bus.ThreadMode;
import com.androidwind.bus.TinyBus;
import com.androidwind.task.TinyTaskExecutor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
        Button btnPostBackground = findViewById(R.id.btn_post_background);
        btnPostBackground.setOnClickListener(this);
        Button btnRelease = findViewById(R.id.btn_release);
        btnRelease.setOnClickListener(this);
        Button btnSecond = findViewById(R.id.btn_second);
        btnSecond.setOnClickListener(this);
        Button btnSynchronized = findViewById(R.id.btn_synchronized);
        btnSynchronized.setOnClickListener(this);
        TinyBus.getInstance().register(this);
        System.out.println("[onCreate]Current thread id is: " + Thread.currentThread().getId());
    }

    @Override
    public void onClick(View v) {
        ((TextView) findViewById(R.id.tv_hello)).setText("");
        switch (v.getId()) {
            case R.id.btn_post:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("[run]Current thread id is: " + Thread.currentThread().getId());
                        TinyBus.getInstance().post(new TestEvent());
                    }
                }).start();
                break;
            case R.id.btn_post_background:
                TinyBus.getInstance().post(new TestBackgroundEvent());
                break;
            case R.id.btn_release:
                TinyBus.getInstance().release(this);
                break;
            case R.id.btn_second:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;
            case R.id.btn_synchronized:
                startActivity(new Intent(MainActivity.this, SynchronizedActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TinyBus.getInstance().release(this);
    }

    //    @Subscriber(threadMode = ThreadMode.MAIN)
    //if the threadMode == ThreadMode.BACKGROUND, you should not update UI in this receiver method
    @Subscriber
    public void onEvent(TestEvent event) {
        System.out.println("[onEvent]Current thread id is: " + Thread.currentThread().getId());
        if (isMainThread()) {
            ((TextView) findViewById(R.id.tv_hello)).setText("OnEvent executed! Current thread id is " + Thread.currentThread().getId());
            ((ImageView) findViewById(R.id.img_hello)).setBackgroundResource(R.mipmap.ic_launcher);
        }
    }

    @Subscriber(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(TestBackgroundEvent event) {
        System.out.println("[onEvent]Current thread id is: " + Thread.currentThread().getId());
        if (isMainThread()) {
            ((TextView) findViewById(R.id.tv_hello)).setText("update in main thread");
        } else {
            TinyTaskExecutor.postToMainThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) findViewById(R.id.tv_hello)).setText("update from background thread");
                }
            });
        }
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
