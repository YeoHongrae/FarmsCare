package com.example.hong.boaaproject.communityActivity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityCommuniFragmentBinding;

public class CommuniFragment extends android.support.v4.app.Fragment {

    ActivityCommuniFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_communi_fragment, container, false);


        binding.trashcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder del = new AlertDialog.Builder(getContext());                            //터치시 삭제확인 박스 표시
                del.setTitle("글을 삭제할까요?");
                del.setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"삭제되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                del.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"취소되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                del.show();
            }
        });

        return binding.getRoot();
    }
}

