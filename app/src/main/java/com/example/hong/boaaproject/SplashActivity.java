package com.example.hong.boaaproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.hong.boaaproject.firstActivity.Index;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextAct = new Intent(SplashActivity.this, Index.class);

                startActivity(nextAct);
                finish();
            }
        }, 5000); // 5초 뒤에 다음 화면으로 넘어감.

    }
}
