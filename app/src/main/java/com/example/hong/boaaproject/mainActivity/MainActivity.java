package com.example.hong.boaaproject.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.menu.Helper;
import com.example.hong.boaaproject.menu.UserInfoUpdate;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();


        TabHost.TabSpec mainTab = tabHost.newTabSpec("MAIN").setIndicator("메인");
        mainTab.setContent(R.id.mainTab);
        tabHost.addTab(mainTab);

        TabHost.TabSpec communicationTab = tabHost.newTabSpec("COMMUNICATION").setIndicator("공유");
        communicationTab.setContent(R.id.communicationTab);
        tabHost.addTab(communicationTab);

        TabHost.TabSpec characterTab = tabHost.newTabSpec("CHARACTER").setIndicator("캐릭터");
        characterTab.setContent(R.id.characterTab);
        tabHost.addTab(characterTab);



    }


    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        menu.add(0, 1, 0, "회원 정보 수정");
        menu.add(0, 2, 0, "도움말");
        menu.add(0, 3, 0, "로그아웃");

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:

                Intent InfoUpdateIntent = new Intent(MainActivity.this, UserInfoUpdate.class);
                MainActivity.this.startActivity(InfoUpdateIntent);
                return true;

            case 2:

                Intent HelperIntent = new Intent(MainActivity.this, Helper.class);
                MainActivity.this.startActivity(HelperIntent);
                return true;

            case 3:

                //TODO 로그아웃되도록

                return true;


        }

        return false;

    }

}

