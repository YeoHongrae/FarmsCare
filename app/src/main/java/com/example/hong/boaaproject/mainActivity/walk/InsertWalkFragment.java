package com.example.hong.boaaproject.mainActivity.walk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hong.boaaproject.R;


public class InsertWalkFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_insert_walk_fragment, container, false);

        Button goalstepsBtn = view.findViewById(R.id.goalsteps);

        goalstepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dlgView = View.inflate(getActivity(), R.layout.goalsteps, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

                dlg.setView(dlgView);
                dlg.show();

            }
        });


        return view;
    }
}
