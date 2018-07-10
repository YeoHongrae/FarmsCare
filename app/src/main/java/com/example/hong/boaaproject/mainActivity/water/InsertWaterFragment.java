package com.example.hong.boaaproject.mainActivity.water;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityInsertWalkFragmentBinding;
import com.example.hong.boaaproject.databinding.ActivityInsertWaterFragmentBinding;
import com.john.waveview.WaveView;

public class InsertWaterFragment extends Fragment {

    ActivityInsertWaterFragmentBinding binding;
    int twt = 0;
    int pg = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_insert_water_fragment, container, false);


        binding.btnplus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                                // 더하기(+) 버튼 누를 경우
                twt = twt + 100;
                pg = pg + 5;
                binding.waveView.setProgress(pg);                                   // wave(수면높이)증가
                if (twt >= 2000) {
                    binding.sbb.setImageResource(R.drawable.sbf);                   // 권장물섭취량(2000ml)가 넘을경우 이미지 변경
                } else if (twt < 0) {
                    twt = 0;
                    pg = 0;
                } else {
                    binding.sbb.setImageResource(R.drawable.sb1);                   // 권장 물 섭취량이 아닐경우 원래이미지로 돌아옴
                }
                binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다."); // 총 섭취량 텍스트 변경
            }
        });
        binding.btnmin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twt = twt - 100;
                pg = pg - 5;
                binding.waveView.setProgress(pg);                                   // 빼기(-) 버튼 누를 경우
                if (twt >= 2000) {
                    binding.sbb.setImageResource(R.drawable.sbf);
                } else if (twt < 0) {                                        //물 섭취량이 0이하로 떨어질 경우에 0 유지
                    twt = 0;
                    pg = 0;
                } else {
                    binding.sbb.setImageResource(R.drawable.sb1);
                }
                binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다.");
            }
        });
        binding.btnplus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twt = twt + 500;
                pg = pg + 25;
                binding.waveView.setProgress(pg);
                if (twt >= 2000) {
                    binding.sbb.setImageResource(R.drawable.sbf);
                } else if (twt < 0) {
                    twt = 0;
                    pg = 0;
                } else {
                    binding.sbb.setImageResource(R.drawable.sb1);
                }
                binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다.");
            }
        });
        binding.btnmin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twt = twt - 500;
                pg = pg - 25;
                binding.waveView.setProgress(pg);
                if (twt >= 2000) {
                    binding.sbb.setImageResource(R.drawable.sbf);
                } else if (twt < 0) {
                    twt = 0;
                    pg = 0;
                } else {
                    binding.sbb.setImageResource(R.drawable.sb1);
                }

                binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다.");

            }


        });

        return binding.getRoot();
    }
}