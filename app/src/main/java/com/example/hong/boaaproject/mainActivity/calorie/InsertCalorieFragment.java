package com.example.hong.boaaproject.mainActivity.calorie;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityInsertCalorieFragmentBinding;

public class InsertCalorieFragment extends Fragment {

    ActivityInsertCalorieFragmentBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_insert_calorie_fragment, container, false);

        binding.breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertBFBtnIntent = new Intent(InsertCalorieFragment.this.getActivity(), SearchCalorie.class);
                InsertCalorieFragment.this.startActivity(insertBFBtnIntent);
            }
        });
        binding.lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertBFBtnIntent = new Intent(InsertCalorieFragment.this.getActivity(), SearchCalorie.class);
                InsertCalorieFragment.this.startActivity(insertBFBtnIntent);
            }
        });
        binding.dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertBFBtnIntent = new Intent(InsertCalorieFragment.this.getActivity(), SearchCalorie.class);
                InsertCalorieFragment.this.startActivity(insertBFBtnIntent);
            }
        });
        binding.snackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertBFBtnIntent = new Intent(InsertCalorieFragment.this.getActivity(), SearchCalorie.class);
                InsertCalorieFragment.this.startActivity(insertBFBtnIntent);
            }
        });


        final EditText setKcal = new EditText(getActivity());


        binding.targetKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder targetKcalDlg = new AlertDialog.Builder(getActivity());
                targetKcalDlg.setMessage("목표 칼로리 설정");
                targetKcalDlg.setView(setKcal);
                targetKcalDlg.setNegativeButton("설정", null);
                targetKcalDlg.show();

                // CUSTOM DIALOG vs ALERTDIALOG vs ACTIVITY 고민해봐야함.
                // 위에 코드 중복 부모VIEW 오류나서 해결해야함. >> ALERTDIALOG 말고 다른 방법으로
            }
        });


        return binding.getRoot();
    }

}
