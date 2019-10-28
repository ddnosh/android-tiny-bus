package com.androidwind.bus.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidwind.bus.Subscriber;
import com.androidwind.bus.TestEvent;
import com.androidwind.bus.TinyBus;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TinyBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TinyBus.getInstance().release(this);
    }

    @Subscriber
    public void onEvent(TestEvent event) {
        System.out.println("[onEvent]Current thread id is: " + Thread.currentThread().getId());
    }

    public void postClick(View view) {
        TinyBus.getInstance().post(new TestEvent());
    }
}
