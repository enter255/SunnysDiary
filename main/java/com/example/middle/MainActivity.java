package com.example.middle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnTakePicture;
    private ImageView mImageView;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePicture = (ImageButton)findViewById(R.id.btnTakePicture);
        //ivPicture = (ImageView)findViewById(R.id.ivPicture);

        btnTakePicture.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(isExistsCameraApplication()){ //카메라 앱 실행
                    Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraApp, 10000);

                    File picture = savePictureFile();
                    cameraApp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));

                     //찍은 사진을 보관할 파일 객체를 만들어서 보냄
                    /*if(picture != null){
                        cameraApp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                        startActivityForResult(cameraApp, 10000);
                    }*/
                }
            }
        });
    }

   /* protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 10000 && resultCode == RESULT_OK){
            //사진을 imageView에 보여줌
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(imagePath);

            factory.inJustDecodeBounds = false;
            factory.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, factory);
            ivPicture.setImageBitmap(bitmap);
        }
    }*/

    //Camera Application이 설치되어 있는지 확인
    private boolean isExistsCameraApplication(){
        PackageManager packageManager = getPackageManager(); //Android의 모든 Application을 얻어옴

        Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라 앱

        List<ResolveInfo>cameraApps = packageManager.queryIntentActivities(cameraApp, PackageManager.MATCH_DEFAULT_ONLY);

        return cameraApps.size() > 0;
    }

    private File savePictureFile(){

            String timestamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
            String fileName = "IMG_" + timestamp;

            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera";
            File pictureStorage = new File(root);

            if(!pictureStorage.exists()){
                pictureStorage.mkdirs();
            }

            try{
                File file = File.createTempFile(fileName, ".jpg", pictureStorage);

                imagePath = file.getAbsolutePath(); // 파일의 절대경로

                /*
                // Get the dimensions of the View
                int targetW = mImageView.getWidth();
                int targetH = mImageView.getHeight();

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imagePath, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                mImageView.setImageBitmap(bitmap);*/

                // 찍힌 사진을 갤러리에 저장
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(imagePath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                return file;
            }catch (IOException e){
                e.printStackTrace();
            }


        return null;
    }
}
