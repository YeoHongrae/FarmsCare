package com.example.hong.boaaproject.firstActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityFirstIndexBinding;
import com.pm10.library.CircleIndicator;

public class Index extends AppCompatActivity {

    ActivityFirstIndexBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_index);

        init();


        binding.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent joinIntent = new Intent(Index.this, SignUp1.class);
                startActivity(joinIntent);
            }
        });


        SpannableString content = new SpannableString("로그인");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.tv2.setText(content);
        binding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new com.example.hong.boaaproject.firstActivity.PagerAdapter(getSupportFragmentManager()));

        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        circleIndicator.setupWithViewPager(viewPager);
    }
}
