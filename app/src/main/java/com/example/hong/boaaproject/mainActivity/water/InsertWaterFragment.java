package com.example.hong.boaaproject.mainActivity.water;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityInsertWaterFragmentBinding;

import java.util.Random;

public class InsertWaterFragment extends Fragment {

    ActivityInsertWaterFragmentBinding binding;



    Random random = new Random();
    int numb = random.nextInt(5);
    final int[] gift = { R.drawable.ex_hat1, R.drawable.ex_hat2, R.drawable.ex_hat3, R.drawable.ex_hat4, R.drawable.ex_hat5 };
    int twt = 0, pg = 0, schance = 1, sbox = 1, nbox = 0;             //schance는 열수있는 기회, sbox는 언박싱여부, nbox는 얻은아이템

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_insert_water_fragment, container, false);




        binding.btnplus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                                // 더하기(+) 버튼 누를 경우
                new Thread() {                                                                      //수면 변화 효과
                    @Override
                    public void run() {
                        for (int g = 0 ; g < 5; g++) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    twt = twt + 20;
                                    pg = pg + 1;
                                    binding.waveView.setProgress(pg);                                       // wave(수면높이)증가

                                    if (twt >= 2000) {
                                        binding.rdbx.setImageResource(R.drawable.inventory_open);
                                        binding.sbb.setImageResource(R.drawable.sbf);                       // 권장물섭취량(2000ml)가 넘을경우 이미지 변경
                                    } else if (twt < 0){
                                        twt = 0;
                                        pg = 0;
                                    }
                                    else{
                                        binding.sbb.setImageResource(R.drawable.sb1);                       // 권장 물 섭취량이 아닐경우 원래이미지로 돌아옴
                                        binding.rdbx.setImageResource(0);
                                    }
                                    binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다.");    // 총 섭취량 텍스트 변경
                                }
                            });
                            SystemClock.sleep(40);
                        }
                    }
                }.start();
            }
        });
        binding.btnmin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        for (int g = 0 ; g < 5; g++) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    twt = twt - 20;
                                    pg = pg - 1;
                                    binding.waveView.setProgress(pg);

                                    if (twt >= 2000) {
                                        binding.sbb.setImageResource(R.drawable.sbf);
                                        binding.rdbx.setImageResource(R.drawable.inventory_open);
                                    } else if (twt < 0){                                            //물 섭취량이 0이하로 떨어질 경우에 0 유지
                                        twt = 0;
                                        pg = 0;
                                    }
                                    else{
                                        binding.sbb.setImageResource(R.drawable.sb1);
                                        binding.rdbx.setImageResource(0);
                                    }
                                    binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다.");

                                }
                            });
                            SystemClock.sleep(80);
                        }
                    }
                }.start();
            }
        });
        binding.btnplus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        for (int g = 0 ; g < 25; g++) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    twt = twt + 20;
                                    pg = pg + 1;
                                    binding.waveView.setProgress(pg);

                                    if (twt >= 2000) {
                                        binding.sbb.setImageResource(R.drawable.sbf);
                                        binding.rdbx.setImageResource(R.drawable.inventory_open);
                                    } else if (twt < 0){
                                        twt = 0;
                                        pg = 0;
                                    }
                                    else{
                                        binding.sbb.setImageResource(R.drawable.sb1);
                                        binding.rdbx.setImageResource(0);
                                    }
                                    binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다.");
                                }
                            });
                            SystemClock.sleep(20);
                        }
                    }
                }.start();
            }
        });
        binding.btnmin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        for (int g = 0 ; g < 25; g++) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    twt = twt - 20;
                                    pg = pg - 1;
                                    binding.waveView.setProgress(pg);

                                    if (twt >= 2000) {
                                        binding.sbb.setImageResource(R.drawable.sbf);
                                        binding.rdbx.setImageResource(R.drawable.inventory_open);
                                    } else if (twt < 0) {
                                        twt = 0;
                                        pg = 0;
                                    }
                                    else{
                                        binding.sbb.setImageResource(R.drawable.sb1);
                                        binding.rdbx.setImageResource(0);
                                    }
                                    binding.totalwt.setText("오늘은 총 " + twt + "ml의 물을 섭취했습니다.");
                                }
                            });
                            SystemClock.sleep(20);
                        }
                    }
                }.start();

            }


        });

        binding.rdbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(schance == 1){
                    AlertDialog.Builder aa = new AlertDialog.Builder(getContext());                 //다이얼로그 설정
                    final View giftxml = View.inflate(getContext(), R.layout.gift_box, null);       //로티애니메이션 xml을가져옴
                    aa.setView(giftxml);                                                            //애니메이션xml을 다이얼로그안에 적용
                    aa.setTitle("선물이 도착했어요!");

                    final int rst = gift[numb];                                                     //랜덤으로 아이템 설정
                    final LottieAnimationView lt = (LottieAnimationView) giftxml.findViewById(R.id.lottie);
                    final ImageView rnd = (ImageView) giftxml.findViewById(R.id.rand1);
                    lt.playAnimation();                                                             //애니메이션 실행
                    lt.loop(true);                                                                  //애니메이션 반복

                    aa.setCancelable(false);
                    aa.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(sbox ==1){
                                Toast.makeText(getActivity(),"열지않은 상자는 인벤토리에 저장됩니다!",Toast.LENGTH_LONG).show();
                                nbox = 0;
                            }

                        }
                    });

                    lt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(),"이것이 나왔습니다!",Toast.LENGTH_LONG).show();
                            lt.setVisibility(View.GONE);                                                //애니메이션 치우기
                            rnd.setImageResource(rst);                                                  //랜덤으로 가져온 아이템 보여주기
                            sbox = 0;
                            nbox = rst;
                        }
                    });aa.show();


                    schance--;
                }else {
                    Toast.makeText(getActivity(),"기회를 모두 소진하였습니다!",Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getActivity(),""+sbox,Toast.LENGTH_LONG).show();
            }
        });


        return binding.getRoot();
    }

}