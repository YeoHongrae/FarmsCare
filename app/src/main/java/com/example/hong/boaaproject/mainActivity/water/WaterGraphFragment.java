package com.example.hong.boaaproject.mainActivity.water;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityGraphWaterFragmentBinding;

public class WaterGraphFragment extends Fragment {

    ActivityGraphWaterFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_graph_water_fragment, container, false);

        String[] datas = getResources().getStringArray(R.array.spinner_array);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, datas);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(aa);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        binding.barchart.setImageResource(R.drawable.barchart);
                        break;
                    case 1:
                        binding.barchart.setImageResource(R.drawable.barchart2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();
    }

}
