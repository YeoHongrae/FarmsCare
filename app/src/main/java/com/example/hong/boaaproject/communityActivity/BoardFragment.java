package com.example.hong.boaaproject.communityActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hong.boaaproject.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoardFragment extends android.support.v4.app.Fragment {

    RecyclerView.LayoutManager manager;
    List<BoardModel> boardModels;
    BoardRecyclerAdapter boardRecyclerAdapter;
    RecyclerView recyclerView;
    FloatingActionButton btnWriting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        btnWriting = view.findViewById(R.id.btnWriting);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        boardModels = new ArrayList<>();
        boardRecyclerAdapter = new BoardRecyclerAdapter(getContext(), boardModels);


        new BackgroundTask().execute();

        btnWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BoardFragment.this.getActivity(), WritingActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> { // 무슨 클래스임.

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://jbh9730.cafe24.com/BoardList.php";
        }

        @Override
        protected String doInBackground(Void... voids) { // 서버에서 데이터를 받아오는 함수
            try {

                URL url = new URL(target); // 해당 서버 연결
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); // 넘어오는 결과값들을 그대로 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // 버퍼에 해당 인풋스트림의 내용을 담아서 저장
                String temp; // 템프에 하나씩 읽어와서
                StringBuilder stringBuilder = new StringBuilder(); // 스트링 형태로 저장

                while ((temp = bufferedReader.readLine()) != null) {//버퍼에서 한줄씩 읽으면서 템프에 넣는다

                    stringBuilder.append(temp + "\n"); //한줄씩 추가

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect(); //처리 끝나면 close

                return stringBuilder.toString().trim(); // 스트링 내용을 반환시켜줌

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) { //
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String result) { // 해당 결과를 처리해주는 함수

            int count = 0;
            String boardContent, boardName, boardDate;

            try {

                JSONObject jsonObject = new JSONObject(result); // 응답부분 처리
                JSONArray jsonArray = jsonObject.getJSONArray("response"); // 각각의 게시판이 response에 담긴다

                while (count < jsonArray.length()) { // 전체 검색

                    JSONObject object = jsonArray.getJSONObject(count); // 현재 배열의 원소값 저장
                    boardContent = object.getString("boardContent"); //boardContent 부분을 가져옴
                    boardName = object.getString("boardName");
                    boardDate = object.getString("boardDate");
                    BoardModel boardModel = new BoardModel(boardContent, boardName, boardDate); // 하나의 게시물에 관한 객체 생성
                    boardModels.add(boardModel); // 리스트에 추가
                    count++; // 전체 검색

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            recyclerView.setAdapter(boardRecyclerAdapter); // 여기서 어댑터 연결해야지 리사이클러뷰가 뜸..

        }
    }
}

