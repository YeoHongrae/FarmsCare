package com.example.hong.boaaproject.firstActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.hong.boaaproject.R;

public class Hint extends AppCompatActivity {

    ImageButton imgPrev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_hint);

        Intent intent = getIntent();

        imgPrev = (findViewById(R.id.imgPrev));


        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

