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

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Register2Activity extends AppCompatActivity {

    ActivityRegister2Binding a;
    private ArrayAdapter<String> adapter;
    String userID, userHeight, userWeight, userGender, userBirth, currentPhotoPath;

    private static final int MY_PERMISSION_CAMERA = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final int REQUEST_TAKE_ALBUM = 3;
    private static final int REQUEST_IMAGE_CROP = 4;

    Uri imageUri;
    Uri photoURI, albumURI;

    public static Context mContext; // 다른 액티비티에서 현재 액티비티의 함수 호출을 위한 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a = DataBindingUtil.setContentView(this, R.layout.activity_register2);
        mContext = this;

        genderSelect(); // 성별 선택

        final String[] year = getResources().getStringArray(R.array.Year);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, year);
        a.spnYear.setAdapter(adapter); //TODO 스피너 좀 더 이쁘게?

        a.btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 추가정보 등록 완료 ( 서버 연동 )
                register2Complete();

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


    // 다이얼로그 생성 함수
    private void getDialog() {

        SelectDialog selectDialog = new SelectDialog(this); // 해당 다이얼로그의 자바파일
        selectDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.d("REQUEST_TAKE_PHOTO", "OK");
                        galleryAddPic();

                        a.civUserPicture.setImageURI(imageUri);
                    } catch (Exception e) {
                        Log.d("REQUEST_TAKE_PHOTO", e.toString());
                    }


                } else {
                    Toast.makeText(Register2Activity.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

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

            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK) {

                    galleryAddPic();
                    a.civUserPicture.setImageURI(albumURI); //TODO 공유탭에서도 사용하려면 액티비티 하나 만들고, 서버에 올리고 받고 ,..?
                }
                break;
        }
    }

    public void captureCamera() {

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (Exception e) {
                    Log.d("captureCamera ERROR : ", e.toString());
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

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "BOAA");

        if (!storageDir.exists()) {
            Log.d("currentPhotoPath : ", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        currentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;

    }

    public void getAlbum() {

        Log.d("getAlbum", "CALL");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);

    }

    private void galleryAddPic() {
        Log.d("galleryAddPic : ", "CALL");
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        intent.setData(contentUri);
        sendBroadcast(intent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void cropImage() {
        Log.d("cropImage", "CALL");
        Log.d("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(photoURI, "image/*");
        intent.putExtra("outputX", 200);
        intent.putExtra("outputy", 200);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectX", 1);
        intent.putExtra("scale", true);
        intent.putExtra("output", albumURI);
        startActivityForResult(intent, REQUEST_IMAGE_CROP);
    }

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

    private void register2Complete() {

        KeepLoginActivity keepLoginActivity = new KeepLoginActivity(this);

        userID = keepLoginActivity.getUserID();
        userHeight = a.etHeight.getText().toString();
        userWeight = a.etWeight.getText().toString();
        userBirth = a.spnYear.getSelectedItem().toString();

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
        Register2Request register2Request = new Register2Request(userID, userHeight, userWeight, userGender, userBirth, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Register2Activity.this);
        queue.add(register2Request);
    }

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
