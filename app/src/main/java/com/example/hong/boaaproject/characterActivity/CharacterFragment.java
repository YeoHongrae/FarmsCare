package com.example.hong.boaaproject.characterActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.FragmentCharacterBinding;

import java.util.Random;

import rebus.bottomdialog.BottomDialog;

/**
 * Created by gksak on 2018-06-05.
 */

public class CharacterFragment extends Fragment {

    FragmentCharacterBinding b;
    BottomDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        b = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false);

        b.character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();                                                //캐릭터를 터치하면 랜덤으로 메세지 변경
                int nb = random.nextInt(6);

                switch (nb) {
                    case 1:
                        b.talk.setText("소풍나갈까?");
                        break;
                    case 2:
                        b.talk.setText("사과는 먹었나 친구!");
                        break;
                    case 3:
                        b.talk.setText("오늘도 화이팅이야~!");
                        break;
                    case 4:
                        b.talk.setText("나는 항상 네편이야");
                        break;
                    case 5:
                        b.talk.setText("우산 챙겨야하나?");
                        break;
                }
            }
        });
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        b.inventory.setOnClickListener(new View.OnClickListener() {                                                       //인벤토리 클릭할 경우 뜨는 창
            @Override
            public void onClick(View view) {
                dialog = new BottomDialog(getContext());                                                                //BottomDialog 호출
                dialog.title("인벤토리");                                                                               //다이얼로그 제목
                dialog.canceledOnTouchOutside(true);                                                                   //다이얼로그 바깥쪽을 터치시 닫기
                dialog.cancelable(true);                                                                               //back키를 누를경우 닫기
                dialog.inflateMenu(R.layout.itmenu);                                                                   //메뉴를 적용할 xml 호출
                dialog.setOnItemSelectedListener(new BottomDialog.OnItemSelectedListener() {                           //다이얼로그의 터치리스너
                    @Override
                    public boolean onItemSelected(int id) {                                                              //각 항목의 아이템설정, 반응설정
                        switch (id) {
                            case R.id.hat_menu:                                                                        //모자항목 선택 시
                                dialog = new BottomDialog(getContext());                                               //새로운 바텀다이얼로그 호출
                                dialog.title("모자를 골라주세요.");                                                     //새로운 제목
                                dialog.canceledOnTouchOutside(true);
                                dialog.cancelable(true);
                                dialog.inflateMenu(R.layout.itmenu_hat);                                               //모자이미지 메뉴를 저장한 xml으로 호출
                                dialog.setOnItemSelectedListener(new BottomDialog.OnItemSelectedListener() {           //각 항목을 선택할 경우 변화과정.
                                    @Override
                                    public boolean onItemSelected(int id) {
                                        switch (id){
                                            case R.id.h1:
                                                Toast.makeText(getContext(),"선물상자 리본", Toast.LENGTH_SHORT).show();
                                                b.wear.setImageResource(R.drawable.hat1);
                                                return true;
                                            case R.id.h2:
                                                Toast.makeText(getContext(),"직접 키운 장미", Toast.LENGTH_SHORT).show();
                                                b.wear.setImageResource(R.drawable.hat2);
                                                return true;
                                            case R.id.h3:
                                                Toast.makeText(getContext(),"산타한테서 뺏은 모자", Toast.LENGTH_SHORT).show();
                                                b.wear.setImageResource(R.drawable.hat3);
                                                return true;
                                            case R.id.h4:
                                                Toast.makeText(getContext(),"도토리의 뚝배기", Toast.LENGTH_SHORT).show();
                                                b.wear.setImageResource(R.drawable.hat4);
                                                return true;
                                            case R.id.h5:
                                                Toast.makeText(getContext(),"인싸전용 헤드셋!", Toast.LENGTH_SHORT).show();
                                                b.wear.setImageResource(R.drawable.hat5);
                                                return true;
                                            case R.id.h6:
                                                Toast.makeText(getContext(),"모자를 벗었습니다.", Toast.LENGTH_SHORT).show();
                                                b.wear.setImageResource(R.drawable.wear);
                                                return true;

                                        }
                                        return false;
                                    }
                                });dialog.show();

                                return true;

                            case R.id.dress:
                                Toast.makeText(getContext(), "가진 옷이 없습니다!", Toast.LENGTH_SHORT).show();
                                return true;

                            case R.id.special:
                                Toast.makeText(getContext(), "아직 준비중입니다..", Toast.LENGTH_SHORT).show();         //준비가 안된 항목은 메세지가 뜨고 다이얼로그 유지.
                                return false;
                        }
                        return false;
                    }
                });
                dialog.show();
            }
        });

/*
        inventory.setOnClickListener(new View.OnClickListener() {                                   //인벤토리 클릭할 경우 뜨는 창
            @Override
            public void onClick(View view) {
                AlertDialog.Builder inv = new AlertDialog.Builder(getContext());                    //다이얼로그 설정
                View inventoryList = View.inflate(getContext(), R.layout.inventory_list, null);     //그리드뷰 xml를가져옴
                inv.setView(inventoryList);                                                         //그리드뷰를 다이얼로그안에 적용
                inv.setTitle("아이템을 골라주세요");

                ImageButton hat1 = (ImageButton) inventoryList.findViewById(R.id.hat1);             //그리드뷰xml 이미지 호출
                ImageButton hat2 = (ImageButton) inventoryList.findViewById(R.id.hat2);
                ImageButton hat3 = (ImageButton) inventoryList.findViewById(R.id.hat3);
                ImageButton hat4 = (ImageButton) inventoryList.findViewById(R.id.hat4);
                ImageButton hat5 = (ImageButton) inventoryList.findViewById(R.id.hat5);

                hat1.setEnabled(true);                                                              //이미지버튼 클릭 허용
                hat2.setEnabled(true);
                hat3.setEnabled(true);
                hat4.setEnabled(true);
                hat5.setEnabled(true);

                hat1.setOnClickListener(new View.OnClickListener() {                                //각 아이템 착용
                    @Override
                    public void onClick(View view) {
                        wear.setImageResource(R.drawable.hat1);                                     //해당 아이콘 클릭시 이미지 변경
                        Toast.makeText(getActivity(),"리본 착용!",Toast.LENGTH_LONG).show();         //이미지 변경 알림 텍스트
                    }
                });

                hat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wear.setImageResource(R.drawable.hat2);
                        Toast.makeText(getActivity(),"장미 착용!",Toast.LENGTH_LONG).show();
                    }
                });

                hat3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wear.setImageResource(R.drawable.hat3);
                        Toast.makeText(getActivity(),"산타모자 착용!",Toast.LENGTH_LONG).show();
                    }
                });

                hat4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wear.setImageResource(R.drawable.hat4);
                        Toast.makeText(getActivity(),"도토리모자 착용!",Toast.LENGTH_LONG).show();
                    }
                });

                hat5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wear.setImageResource(R.drawable.hat5);
                        Toast.makeText(getActivity(),"헤드셋 착용!",Toast.LENGTH_LONG).show();
                    }
                });

                inv.setNegativeButton("초기화",new DialogInterface.OnClickListener() {               //버튼 누를경우 캐릭터 이미지 초기화
                            public void onClick(DialogInterface dialog, int which) {
                                wear.setImageResource(R.drawable.wear);
                                Toast.makeText(getContext(),"초기화 되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                inv.setPositiveButton("완료",new DialogInterface.OnClickListener() {                 //다이얼로그 창 닫기
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                inv.show();

            }
        });

*/



        return b.getRoot();
    }

}
