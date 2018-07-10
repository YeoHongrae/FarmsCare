package com.example.hong.boaaproject.mainActivity.calorie;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hong.boaaproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SearchCalorie extends AppCompatActivity {

    TextView txtView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_calorie);

        Toolbar toolBar = findViewById(R.id.toolBar);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽 상단에 뒤로가기 버튼 생성


        txtView = findViewById(R.id.text);

        String serviceUrl =
                "http://openapi.foodsafetykorea.go.kr/api/4b05e015a9004e25960c/I0750/xml/1/5";

        String serviceKey = "4b05e015a9004e25960c";
        serviceKey = URLEncoder.encode(serviceKey);
        String NUM = "1";
        String strUrl = serviceUrl + "ServiceKey=" + serviceKey + "&NUM=" + NUM;
        new DownloadWebpageTask().execute(strUrl);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // 뒤로가기 버튼 적용시 홈으로.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                return (String) downloadUrl((String) strings[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        protected void onPostExecute(String result) {
            txtView.append(result + "\n");
            txtView.append("============= 파싱결과 ============");

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();
                boolean bSet = false;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {

                    } else if (eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if (tag_name.equals("NUM") || tag_name.equals("FOOD_CD"))
                            bSet = true;

                    } else if (eventType == XmlPullParser.TEXT) {
                        if (bSet) {
                            String content = xpp.getText();
                            txtView.append(content + "\n");
                            bSet = false;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {

                    }
                    eventType = xpp.next();

                }

            } catch (Exception e) {
                txtView.setText(e.getMessage());
            }
        }

        private String downloadUrl(String myurl) throws IOException {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }
                return page;
            } finally {
                conn.disconnect();
            }
        }
    }
}


