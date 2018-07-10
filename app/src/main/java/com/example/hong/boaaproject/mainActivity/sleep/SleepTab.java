package com.example.hong.boaaproject.mainActivity.sleep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.example.hong.boaaproject.R;

public class SleepTab extends AppCompatActivity {

    Toolbar toolbar;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_sleep);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();


        TabHost.TabSpec insertTab = tabHost.newTabSpec("INSERT").setIndicator("수면량");
        insertTab.setContent(R.id.insertTab);
        tabHost.addTab(insertTab);

        TabHost.TabSpec graphTab = tabHost.newTabSpec("GRAPH").setIndicator("수면 그래프");
        graphTab.setContent(R.id.graphTab);
        tabHost.addTab(graphTab);


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

