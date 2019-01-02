package com.androidwind.bus.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        TinyBus.getInstance().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post:
                TinyBus.getInstance().post(new TestEvent());
                break;
            case R.id.btn_release:

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

    }
}
