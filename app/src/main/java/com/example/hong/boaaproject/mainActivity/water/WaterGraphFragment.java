package com.example.hong.boaaproject.mainActivity.water;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityGraphWaterFragmentBinding;
import com.example.hong.boaaproject.mainActivity.MyMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class WaterGraphFragment extends Fragment {

    //ActivityGraphWaterFragmentBinding binding;
    MyMarkerView marker;
    Spinner spinner;
    BarChart waterChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.activity_graph_water_fragment, container, false);
            waterChart = view.findViewById(R.id.waterChart);
            spinner = view.findViewById(R.id.spinner);


            // binding = DataBindingUtil.inflate(inflater, R.layout.activity_graph_water_fragment, container, false);
            marker = new MyMarkerView(getContext(), R.layout.markerview_root);

            String[] datas = getResources().getStringArray(R.array.spinner_array);
            ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, datas);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(aa);
            marker.setChartView(waterChart);
            waterChart.setMarker(marker);

            //리스트에 BarEntry타입을 선언. 그래프의 x축, y축 값을 결정
            List<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(1, 1));
            entries.add(new BarEntry(2,2));
            entries.add(new BarEntry(3,0));
            entries.add(new BarEntry(4, 4));
            entries.add(new BarEntry(5, 3));

            //DataSet을 만든다. 그래프의 선 색 등등의 속성을 정할 수 있다.
            BarDataSet barDataSet = new BarDataSet(entries, "속성명1");

            barDataSet.setBarBorderWidth(2);
            barDataSet.setBarBorderColor(Color.BLUE);
            barDataSet.setDrawValues(false); //?

            BarData barData = new BarData(barDataSet);
            waterChart.setData(barData);

            //x축과 y축의 효과
            XAxis xAxis = waterChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.BLACK);
            xAxis.enableGridDashedLine(8, 24, 0); //?

            YAxis yLAxis = waterChart.getAxisLeft();
            yLAxis.setTextColor(Color.BLACK);

            YAxis yRAxis = waterChart.getAxisRight();
            yRAxis.setDrawLabels(false);
            yRAxis.setDrawAxisLine(false);
            yRAxis.setDrawGridLines(false);

            Description description = new Description(); //?
            description.setText("");


            //차트에 효과넣기.
            waterChart.setDoubleTapToZoomEnabled(false);
            waterChart.setDrawGridBackground(false);
            waterChart.setDescription(description);
            waterChart.animateY(2000, Easing.EasingOption.EaseInCubic);
            waterChart.invalidate();

        return view;
      //  return binding.getRoot();



    }

}
