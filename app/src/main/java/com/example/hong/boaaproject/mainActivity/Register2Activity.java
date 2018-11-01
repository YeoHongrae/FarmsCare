package com.example.hong.boaaproject.mainActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hong.boaaproject.R;
import com.example.hong.boaaproject.databinding.ActivityRegister2Binding;
import com.example.hong.boaaproject.menu.SelectDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Register2Activity extends AppCompatActivity {

    //TODO 이미지 호출 부분 최적화

    private ActivityRegister2Binding a;
    private ArrayAdapter<String> adapter;
    private String userID, userHeight, userWeight, userGender, userBirth, userImgURL, currentPhotoPath, imageFileName, uploadFileName, uploadFilePath, uploadServerUri;

    private static final int MY_PERMISSION_CAMERA = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final int REQUEST_TAKE_ALBUM = 3;
    private static final int REQUEST_IMAGE_CROP = 4;
    private int serverResponseCode = 0;

    private Uri imageUri;
    private Uri photoURI, albumURI;




    public static Context mContext; // 다른 액티비티에서 현재 액티비티의 함수 호출을 위한 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a = DataBindingUtil.setContentView(this, R.layout.activity_register2);
        mContext = this;
        uploadServerUri = "http://jbh9730.cafe24.com/UploadToServer.php";

        genderSelect(); // 성별 선택

        final String[] year = getResources().getStringArray(R.array.Year);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, year);
        a.spnYear.setAdapter(adapter); //TODO 스피너 좀 더 이쁘게?

        a.btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 추가정보 등록 완료 ( 서버 연동 )
                register2Complete();

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                //Log.d("HONG","UPLOADING");
                            }
                        });

                        uploadFile(uploadFilePath + "" + uploadFileName);

                    }
                }).start();
            }
        });

        a.civUserPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다이얼로그 호출 > 앨범 OR 카메라 함수 호출
                getDialog();
                checkPermission();
            }
        });
    }

    public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;

        HttpURLConnection conn;
        DataOutputStream dos;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            //Log.d("HONG", "UPLOAD FILE" + "SOURCE FILE NOT EXIST : " + uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Log.d("HONG", "UPLOAD FILE" + "SOURCE FILE NOT EXIST : " + uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(uploadServerUri);

                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = conn.getResponseCode();
                String serverResopnseMessage = conn.getResponseMessage();

                //Log.i("HONG", "UPLOADfILE" + "HTTP RESPONSE IS : " + serverResopnseMessage + ":" + serverResponseCode);

                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //String msg = "FILE UPLOAD COMPLETED.\n\n SEE UPLOADED FILE HERE : \n\n" + uploadFileName;
                            // Toast.makeText(Register2Activity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(Register2Activity.this, "MALFORMED URL EXCEPTION", Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e("HONG", "UPLOAD FILE TO SERVER" + "ERROR");
            } catch (Exception e) {

                e.printStackTrace();
/*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("HONG", "GOT EXCEPTION : SEE LOGCAT");

                    }
                });
                Log.e("HONG", "UPLOAD FILE TO SERVER EXCEPTION" + "EXCEPTION : " + e.getMessage(), e);*/
            }

            return serverResponseCode;

        }

    }

    // 다이얼로그 생성 함수
    private void getDialog() {

        SelectDialog selectDialog = new SelectDialog(this); // 해당 다이얼로그의 자바파일
        selectDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 사진 촬영
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        //Log.d("REQUEST_TAKE_PHOTO", "OK");
                        galleryAddPic();

                        a.civUserPicture.setImageURI(imageUri);


                    } catch (Exception e) {
                        Log.d("REQUEST_TAKE_PHOTO", e.toString());
                    }


                } else {
                    Toast.makeText(Register2Activity.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            // 갤러리 호출
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    if (data.getData() != null) {
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage();
                        } catch (Exception e) {
                            Log.d("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;

            // 크롭 호출
            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK) {

                    galleryAddPic();
                    a.civUserPicture.setImageURI(albumURI); //TODO 공유탭에서도 사용하려면 액티비티 하나 만들고, 서버에 올리고 받고 ,..?
                }
                break;
        }
    }


    //카메라 호출 함수
    public void captureCamera() {

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라 호출

            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (Exception e) {
                    //Log.d("captureCamera ERROR : ", e.toString());
                }

                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imageUri = providerURI;

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    // 이미지 파일 만드는 메소드
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + ".jpg"; // 이미지 이름 설정
        File imageFile;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "BOAA"); // 저장 경로

        uploadFilePath = storageDir.getAbsolutePath() + "/";
        uploadFileName = imageFileName;


        if (!storageDir.exists()) { // 존재하지 않다면 디렉토리 생성
            //Log.d("currentPhotoPath : ", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName); // 파일위치, 파일명
        currentPhotoPath = imageFile.getAbsolutePath(); // 파일의 절대 경로 ?

        return imageFile;

    }

    // 갤러리 호출 함수
    public void getAlbum() {

        //Log.d("getAlbum", "CALL");
        Intent intent = new Intent(Intent.ACTION_PICK); // 갤러리 실행
        intent.setType("image/*"); // image 타입의 파일들 모두 출력
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);

    }

    // 앨범에 사진 저장
    private void galleryAddPic() {
        //Log.d("galleryAddPic : ", "CALL");
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        intent.setData(contentUri);
        sendBroadcast(intent); // 폰의 앨범에 크롭된 사진을 갱신하는 함수, 안쓰면 크롭된 사진을 저장해도 보이지 않는다.
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 이미지 자르기 호출
    public void cropImage() {
        //Log.d("cropImage", "CALL");
        //Log.d("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);

        Intent intent = new Intent("com.android.camera.action.CROP"); // 크롭 화면 호출

        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(photoURI, "image/*"); // photoURI에 저장
        intent.putExtra("outputX", 200);
        intent.putExtra("outputy", 200);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectX", 1);
        intent.putExtra("scale", true);
        intent.putExtra("output", albumURI);
        startActivityForResult(intent, REQUEST_IMAGE_CROP); // 크롭 case 문으로 이동
    }

    //권한 메소드
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package : " + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] < 0) {
                        Toast.makeText(Register2Activity.this, "해당 권한을 활성화하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..?
                break;
        }
    }

    // 유저 정보 등록
    private void register2Complete() {

        KeepLoginActivity keepLoginActivity = new KeepLoginActivity(this);

        userID = keepLoginActivity.getUserID();
        userHeight = a.etHeight.getText().toString();
        userWeight = a.etWeight.getText().toString();
        userBirth = a.spnYear.getSelectedItem().toString();
        userImgURL = "http://jbh9730.cafe24.com/uploads/" + uploadFileName;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        Toast.makeText(Register2Activity.this, "추가정보 등록에 성공했습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register2Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        Register2Request register2Request = new Register2Request(userID, userHeight, userWeight, userGender, userBirth, userImgURL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Register2Activity.this);
        queue.add(register2Request);
    }

    //성별 선택
    private void genderSelect() {

        a.tvFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a.tvFemale.setBackgroundColor(getResources().getColor(R.color.mainColor));
                a.tvFemale.setTextColor(Color.WHITE);
                a.tvMale.setBackgroundColor(Color.WHITE);
                a.tvMale.setTextColor(Color.BLACK);
                userGender = a.tvFemale.getText().toString();

            }
        });

        a.tvMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a.tvMale.setBackgroundColor(getResources().getColor(R.color.mainColor));
                a.tvMale.setTextColor(Color.WHITE);
                a.tvFemale.setBackgroundColor(Color.WHITE);
                a.tvFemale.setTextColor(Color.BLACK);
                userGender = a.tvMale.getText().toString();

            }
        });
    }
}
