package com.example.hong.boaaproject.mainActivity.calorie;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.example.hong.boaaproject.R;


public class CalorieTab extends AppCompatActivity {

    Toolbar toolbar;
    TabHost tabhost;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_calorie);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabhost = (TabHost) findViewById(R.id.tabhost);
        tabhost.setup();

        TabHost.TabSpec insertTab = tabhost.newTabSpec("INSERT").setIndicator("칼로리 섭취량");
        insertTab.setContent(R.id.insertTab);
        tabhost.addTab(insertTab);

        TabHost.TabSpec graphTab = tabhost.newTabSpec("GRAPH").setIndicator("칼로리 그래프");
        graphTab.setContent(R.id.graphTab);
        tabhost.addTab(graphTab);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                // NavUtils.navigateUpFromSameTask(this);

                finish();

                return true;

        }

        return super.onOptionsItemSelected(item);


    }

    ;
}
