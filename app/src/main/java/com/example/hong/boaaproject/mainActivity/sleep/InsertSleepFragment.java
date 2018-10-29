package com.example.hong.boaaproject.mainActivity.sleep;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hong.boaaproject.R;

public class InsertSleepFragment extends Fragment implements View.OnClickListener {

    Point size;
    Button btnInit;
    double[] clockDegree;
    PointF centerPoint, init;
    PointF[] numberPoint;
    PointF clicked1Point, clicked2Point;

    ImageView ivClock;
    int[] clickedNum;
    TextView testNumResult;
    TextView[] tvTime;
    int radius = 300;
    int[] displaySize;
    float currentAngle = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_sleep, container, false);

        //init
        size = new Point();
        init = new PointF(0, 0);
        clicked1Point = new PointF(0, 0);
        clicked2Point = new PointF(0, 0);
        clickedNum = new int[]{-1, -1};
        btnInit = view.findViewById(R.id.btnInit);
        ivClock = view.findViewById(R.id.ivClockTracking);

        tvTime = new TextView[]{
                view.findViewById(R.id.tv0),
                view.findViewById(R.id.tv1),
                view.findViewById(R.id.tv2),
                view.findViewById(R.id.tv3),
                view.findViewById(R.id.tv4),
                view.findViewById(R.id.tv5),
                view.findViewById(R.id.tv6),
                view.findViewById(R.id.tv7),
                view.findViewById(R.id.tv8),
                view.findViewById(R.id.tv9),
                view.findViewById(R.id.tv10),
                view.findViewById(R.id.tv11)
        };

        clockDegree = new double[12];
        testNumResult = view.findViewById(R.id.testNumResult);

        //getSize메소드로 화면크기를 받아옴
        displaySize = getSize();

        //화면 크기에 따른 정 중앙의 좌표를 계산
        centerPoint = new PointF(displaySize[0] / 2, displaySize[1] / 2);
        numberPoint = new PointF[12];

        //PointF배열 초기화
        for (int i = 0; i < 12; i++) {
            numberPoint[i] = new PointF();
        }

        //clockDegree의 index가 시계의 숫자가 된다.
        //숫자 0은 중심(0,0)을 기준으로 90도, 1은 60도, 2는 30도, 3은 0도, 4는 330도...에 배치되어있다는 경향성을 파악해 for문을 다음과 같이 설계했다.
        for (int i = 0; i < clockDegree.length; i++) {
            double num = 90.0 - i * 30;
            if (num < 0.0) {
                num = 360.0 + num;
            }
            clockDegree[i] = num;
        }

        //메소드 실행. 각 숫자의 좌표를 계산후 화면에 배치한다.
        setNumberPoint();
        setNumber(tvTime);

        //시계이미지 위치 세팅
        ivClock.setX(centerPoint.x - radius);
        ivClock.setY(centerPoint.y - radius);
        ivClock.getLayoutParams().width = radius * 2;
        ivClock.getLayoutParams().height = radius * 2;
        ivClock.setClickable(false);

        //TextView에 클릭리스너 등록 : 중복 코드를 방지하기 위해 OnClickListener 인터페이스를 사용함
        for (int i = 0; i < tvTime.length; i++) {
            tvTime[i].setOnClickListener(this);
        }

        //확인을 쉽게 하기 위한 초기화 버튼의 이벤트 처리
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        return view;
    }

    //클릭한 TextView의 숫자를 받아와 화면에 나타내는 메소드
    public void onClick(View view) {

        for (int i = 0; i < tvTime.length; i++) {
            //클릭한 텍스트뷰의 id가 일치하고 clickedNum배열의 초기값(-1)과 일치할때
            if (clickedNum[0] == -1 && view.getId() == tvTime[i].getId()) {
                //클릭한 숫자를 clickedNum배열에 삽입 및 화면에 출력
                clickedNum[0] = Integer.parseInt(tvTime[i].getText().toString());
                testNumResult.setText(String.valueOf(clickedNum[0]));
                clicked1Point = numberPoint[i]; //클릭한 첫번째 숫자의 좌표를 clicked1Point에 저장

            } else if (clickedNum[1] == -1 && view.getId() == tvTime[i].getId()) {
                clickedNum[1] = Integer.parseInt(tvTime[i].getText().toString());
                testNumResult.setText(String.valueOf(clickedNum[0] + "+" + clickedNum[1]));
                double angle = calAngle(clickedNum);
                drawTracking(clickedNum[0], angle, centerPoint);
            }
        }
    }

    private double calAngle(int[] clock) {
        double angle;
        int firstNum, secNum;
        firstNum = clock[0];
        secNum = clock[1];

        if (secNum < firstNum) {
            secNum = secNum + 12;
        }

        angle = (secNum - firstNum) * 30;

        return angle;
    }

    //정보 초기화
    private void init() {
        clickedNum[0] = -1;
        clickedNum[1] = -1;
        testNumResult.setText("");

    }

    //숫자의 좌표를 삼각함수를 이용해 계산해주는 메소드
    private void setNumberPoint() {
        for (int i = 0; i < numberPoint.length; i++) {
            //숫자가 위치할 좌표(numberPoint)의 x, y좌표에 해당 숫자가 매칭된 각을 계산한다.
            //clockDegree배열에 담긴 숫자들은 전부 각도법 기반이므로, degree2radian메소드를 활용해 호도법으로 바꾼 후 삼각함수 계산
            //임의의 theta값과 반지름 radius를 알 때 삼각형의 밑변은 radius * cos(theta)이고, 삼각형의 높이는 radous * sin(theta)이다.
            //테스트해보니 x좌표계가 반대인듯 싶어 x좌표는 * -1을 해주었음.
            numberPoint[i].x = centerPoint.x - (float) (radius * Math.cos(degree2radian(clockDegree[i]))) * -1;
            numberPoint[i].y = centerPoint.y - (float) (radius * Math.sin(degree2radian(clockDegree[i])));
        }
    }

    //숫자를 화면에 배치
    private void setNumber(TextView[] tvTime) {
        for (int i = 0; i < tvTime.length; i++) {
            tvTime[i].setX(numberPoint[i].x);
            tvTime[i].setY(numberPoint[i].y);
        }
    }

    //화면의 크기를 받아오는 메소드
    private int[] getSize() {
        int w = getResources().getDisplayMetrics().widthPixels;
        int h = getResources().getDisplayMetrics().heightPixels;

        return new int[]{w, h};
    }

    //호를 그리는 메소드
    private void drawTracking(int clickNum, double angle, PointF center) {

        Bitmap bitmap = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final double maxAngle = angle;
        final int cNum = clickNum;

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        final RectF rect = new RectF();
        rect.set(0, 0, 300, 300);

        final double[] arcAngle = new double[]{270, 300, 330, 0, 30, 60, 90, 120, 150, 180, 210, 240};


        ivClock.setImageBitmap(bitmap);
        //호 애니매이션
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentAngle < maxAngle) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            canvas.drawArc(rect, (float) arcAngle[cNum], currentAngle, false, paint);
                            ivClock.invalidate();
                            currentAngle++;
                        }
                    });
                    SystemClock.sleep(15);
                }
            }
        }).start();

    }
    //각도법을 호도법으로
    private double degree2radian(double dg) {
        return (dg * Math.PI / 180.0);
    }
    //호도법을 각도법으로
    private double radian2degree(double rd) {
        return (rd * 180.0 / Math.PI);
    }
}

