package com.example.hong.boaaproject.firstActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityFirstSignup1Binding;


/* 액티비티를 어떻게 관리해야 효율적일지 ..... 액티비티 종료 시점같은 ...
 * */

public class SignUp1 extends AppCompatActivity {

    //MyDBHelper dbHelper;
    //SQLiteDatabase sqlDB;

    String id, name, pw, pwcf, pwHint;
    boolean idCheck, pwCheck = false;
    ActivityFirstSignup1Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_signup1);

        id = binding.etID.getText().toString();
        name = binding.etName.getText().toString();
        pwHint = binding.etHint.getText().toString();

        //아이템 클릭시 이벤트
        binding.hintSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //아이디 중복체크
        binding.tvIdEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:DB로 ID체크
                //아직 DB는 구현이 되지 않았기때문에 그냥 클릭했을때 idCheck를 true로 바꿔준다.
                AlertDialog.Builder dlg = new AlertDialog.Builder(SignUp1.this);
                dlg.setMessage("사용가능한 ID입니다.");
                dlg.setPositiveButton("확인", null);
                dlg.show();
                idCheck = true;
            }
        });

        //패스워드를 확인한다. addTextChangedListener을 사용해 입력된 텍스트가 변경될때마다 TextView를 갱신해준다.
        //참고 : http://ktko.tistory.com/55
        binding.etPwCf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //editText에 입력된 텍스트가 변할때
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pw = binding.etPw.getText().toString();
                pwcf = binding.etPwCf.getText().toString();
                if (pw.equals(pwcf)) {
                    binding.tvEqualCheck.setText("맞게 입력하셨어요!");
                    pwCheck = true;
                } else {
                    binding.tvEqualCheck.setText("다시 입력해주세요!");
                    pwCheck = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //다음으로 버튼. 아이디 중복체크가 되지 않았거나, 패스워드 확인이 되지 않았거나, 모든 정보를 입력하지 않았으면 다음 화면으로 넘어갈 수 없다. 참고 홈페이지 : http://uroa.tistory.com/2
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(SignUp1.this);
                if (!idCheck) {
                    dlg.setMessage("아이디 중복체크를 해주세요.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                } else if (!pwCheck) {
                    dlg.setMessage("패스워드를 정확히 입력해주세요.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                }
                //왠진모르겠는데 오류나서 고칠거임
                /*else if (TextUtils.isEmpty(id)||TextUtils.isEmpty(name)||TextUtils.isEmpty(pw)||TextUtils.isEmpty(pwHint)){
                    dlg.setMessage("모든 정보를 입력해주세요.");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                } */
                else {
                    Intent intent = new Intent(SignUp1.this, SignUp2.class);
                    startActivity(intent);
                }
            }
        });

        //이전으로 돌아가기
        binding.imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp1.this, Index.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

