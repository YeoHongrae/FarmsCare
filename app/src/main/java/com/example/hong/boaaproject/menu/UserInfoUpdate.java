package com.example.hong.boaaproject.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hong.boaaproject.R;

public class UserInfoUpdate extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TextView withdrawalBtn = findViewById(R.id.withdrawal);
        withdrawalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder withdrawalDlg = new AlertDialog.Builder(UserInfoUpdate.this);
                withdrawalDlg.setMessage("정말 탈퇴하시겠습니까?");

                withdrawalDlg.setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //TODO 데이터베이스에서 회원정보 삭제

                        AlertDialog.Builder withdrawalDlg2 = new AlertDialog.Builder(UserInfoUpdate.this);
                        withdrawalDlg2.setMessage("탈퇴 되었습니다.");
                        withdrawalDlg2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //TODO 초기 로그인 화면으로 이동

                            }
                        });



                    }
                });

                withdrawalDlg.setNegativeButton("취소", null);
                withdrawalDlg.show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
