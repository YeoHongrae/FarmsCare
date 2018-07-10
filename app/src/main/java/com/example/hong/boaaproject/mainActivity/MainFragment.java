package com.example.hong.boaaproject.mainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityMainFragmentBinding;
import com.example.hong.boaaproject.mainActivity.calorie.CalorieTab;
import com.example.hong.boaaproject.mainActivity.sleep.SleepTab;
import com.example.hong.boaaproject.mainActivity.walk.WalkTab;
import com.example.hong.boaaproject.mainActivity.water.WaterTab;

public class MainFragment extends android.support.v4.app.Fragment implements SensorEventListener { //SensorEventListener 상속

    ActivityMainFragmentBinding binding;
    int count = 0;
    String str = String.format("%d", count);

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_main_fragment, container, false);
        // 가속도 센서를 이용한 만보기 기능 구현 [출처: http://pulsebeat.tistory.com/44]
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);//센서를 사용하기 위해 시스템서비스를 가져와 SensorManager타입으로 저장
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 엑셀러로미터 센서(가속도 센서)
        binding.walkView.setText(str);
        binding.walkView.setClickable(false); // 해당 뷰 클릭 불가능 설정
        binding.walkView.setFocusable(false); // 해당 뷰 포커스 되지 않도록 설정


        binding.userState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View dlgView = View.inflate(getActivity(), R.layout.statedialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

                dlg.setNegativeButton("닫기", null);
                dlg.setView(dlgView);
                dlg.show();

            }
        });

        binding.userWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userWaterBtnIntent = new Intent(MainFragment.this.getActivity(), WaterTab.class);
                MainFragment.this.startActivity(userWaterBtnIntent);
            }
        });

        binding.userSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userSleepBtnIntent = new Intent(MainFragment.this.getActivity(), SleepTab.class);
                MainFragment.this.startActivity(userSleepBtnIntent);
            }
        });

        binding.footSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent footsteptsBtnIntent = new Intent(MainFragment.this.getActivity(), WalkTab.class);
                MainFragment.this.startActivity(footsteptsBtnIntent);
            }
        });

        binding.userKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userKcalBtnIntent = new Intent(MainFragment.this.getActivity(), CalorieTab.class); // 알아두기. getActivity  https://stackoverflow.com/questions/20241857/android-intent-cannot-resolve-constructor
                MainFragment.this.startActivity(userKcalBtnIntent);


            }
        });

        return binding.getRoot();


    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,      //가속도 센서 리스너 등록
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);         // 해당 리스너 해제
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    } //onAccuracyChanged와 onSensorChanged를 오버라이딩 해줘야 함. 정확도와 센서 정보가 변하면 발생하는 함수

    @Override
    public void onSensorChanged(SensorEvent event) { //흔드는 것을 감지. 가속도센서가 변함에 따라 이 함수가 실행
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) { //센서 종류가 가속도 센서일 때, 이벤트를 처리해줘야 함.
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) { //최근 측정시간과 현재시간을 비교하여 0.1초 이상인 경우, 흔듦 감지

                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
                //가속도의 벡터값을 이용하여 대략적으로 측정(직선 움직임만 측정 가능하여 정확도 조금 떨어지는 단점. 각속도 측정할 수 있는 자이로 센서와 함께쓰면 보완가능)
                if (speed > SHAKE_THRESHOLD) {  //위에서 설정함 초기값: SHAKE_THRESHOLD = 800; 속도가 800 이상인 경우, 흔듦을 감지하도록 설정
                    count++; // 흔듦을 감지하면 걸음 수 올라가고 그 값을 받아서 화면에 출력
                    str = String.format("%d", count);
                    binding.walkView.setText(str);
                }
                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }

        }

    }
}




