package com.example.main;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnTakePicture;
    //private ImageButton btnGetPicture;
    private ImageView ivPicture;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePicture = (ImageButton) findViewById(R.id.btnTakePicture);
        //btnGetPicture = (ImageButton) findViewById(R.id.btnGetPicture);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isExistsCameraApplication()) { //카메라 앱 실행
                    Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraApp, 10000);

                    File picture = savePictureFile();
                    cameraApp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));

                    /*
                    File picture = savePictureFile(); //찍은 사진을 보관할 파일 객체를 만들어서 보냄
                    if(picture != null){
                        cameraApp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                        startActivityForResult(cameraApp, 10000);
                    }*/
                }
            }
        });



    }
    public void albumClick(View view){
        Intent i = new Intent(this, getPicture.class);
        startActivity(i);
    }
    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10000 && resultCode == RESULT_OK) {
            //사진을 imageView에 보여줌
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(imagePath);

            factory.inJustDecodeBounds = false;
            factory.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, factory);
            ivPicture.setImageBitmap(bitmap);
        }


    }
*/
    //Camera Application이 설치되어 있는지 확인
    private boolean isExistsCameraApplication() {
        PackageManager packageManager = getPackageManager(); //Android의 모든 Application을 얻어옴

        Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라 앱

        List<ResolveInfo> cameraApps = packageManager.queryIntentActivities(cameraApp, PackageManager.MATCH_DEFAULT_ONLY);

        return cameraApps.size() > 0;
    }


    private File savePictureFile(){
        //PermissionRequester.Builder requester = new PermissionRequester.Builder(this);

        /*int result = requester.create()
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, 20000, new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {

                    }
                });*/

        //if(result == PermissionRequester.ALREADY_GRANTED || result == PermissionRequester.REQUEST_PERMISSION){
        String timestamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        String fileName = "IMG_" + timestamp;

        File pictureStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MYAPP/");

        if(!pictureStorage.exists()){
            pictureStorage.mkdirs();
        }

        try{
            File file = File.createTempFile(fileName, ".jpg", pictureStorage);

            imagePath = file.getAbsolutePath();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(imagePath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            return file;
        }catch (IOException e){
            e.printStackTrace();
        }
        // }
        return null;
    }

    public void diaryClick(View view){
        Intent i = new Intent(this, diary.class);
        startActivity(i);
        Toast.makeText(this, "test selected", Toast.LENGTH_SHORT).show();
    }
}
