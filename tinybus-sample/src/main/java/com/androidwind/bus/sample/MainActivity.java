package com.androidwind.bus.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidwind.bus.Subscriber;
import com.androidwind.bus.TestEvent;
import com.androidwind.bus.TinyBus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
        Button btnRelease = findViewById(R.id.btn_release);
        btnRelease.setOnClickListener(this);
        Button btnSynchronized = findViewById(R.id.btn_synchronized);
        btnSynchronized.setOnClickListener(this);
        TinyBus.getInstance().register(this);
        System.out.println("[onCreate]Current thread id is: " + Thread.currentThread().getId());
    }

    @Override
    public void onClick(View v) {
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
            case R.id.btn_release:

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

    @Subscriber
    public void onEvent(TestEvent event) {
        System.out.println("[onEvent]Current thread id is: " + Thread.currentThread().getId());
        ((TextView)findViewById(R.id.tv_hello)).setText("OnEvent executed!");
    }
}
