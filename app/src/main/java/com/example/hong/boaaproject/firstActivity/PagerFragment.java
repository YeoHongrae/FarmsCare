package com.example.hong.boaaproject.firstActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hong.boaaproject.R;


public class PagerFragment extends Fragment {

    private int position;

    public static PagerFragment getInstance(int position) {
        PagerFragment helperFragment = new PagerFragment();
        helperFragment.position = position;
        return helperFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_helper_frag, null);



        switch (position){
            case 0:
                ((ImageView)view.findViewById(R.id.position)).setImageResource(R.drawable.ic_face_black_24dp);
                return view;
            case 1:
                ((ImageView)view.findViewById(R.id.position)).setImageResource(R.drawable.ic_chevron_left_black_24dp);
                return view;
            case 2:
                // 사진
                return view;
            case 3:
                //사진
                return view;
            case 4:
                //사진
                return view;
        }




        return view;
    }
}