package com.example.hong.boaaproject.mainActivity.water;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.example.hong.boaaproject.R;

public class WaterTab extends AppCompatActivity {

    Toolbar toolbar;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_water);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 나타나도록 설정


        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();


        TabHost.TabSpec insertTab = tabHost.newTabSpec("INSERT").setIndicator("오늘의 수분섭취량");
        insertTab.setContent(R.id.insertTab);
        tabHost.addTab(insertTab);

        TabHost.TabSpec graphTab = tabHost.newTabSpec("GRAPH").setIndicator("수분 그래프");
        graphTab.setContent(R.id.graphTab);
        tabHost.addTab(graphTab);


    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: //toolbar의 뒤로가기 버튼 클릭 시 동작 [출처: http://ziscuffine.tistory.com/94]

                // NavUtils.navigateUpFromSameTask(this);

                finish();

                return true;

        }

        return super.onOptionsItemSelected(item);


    };
}
