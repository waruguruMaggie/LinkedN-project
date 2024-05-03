package com.symon.linkedn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Navigation navigation = new Navigation(this, MainActivity.this);
        new Handler().postDelayed(() -> {
            navigation.moveToActivity(Login.class);
            finish();
        }, 2000);
    }
}