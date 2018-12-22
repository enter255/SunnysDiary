package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class getCamera extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //Toast.makeText(this, "getCamera", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_camera);
        imageView = findViewById(R.id.photo);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

/*        Button photoButton = (Button) this.findViewById(R.id.take);
        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });
        */
       /* Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }*/
//        photoButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(cameraIntent.resolveActivity(getPackageManager())!=null) {
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                }
//            }
//        });

    }
//    public void onbtnClick(){
////        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(cameraIntent.resolveActivity(getPackageManager())!=null) {
//            startActivityForResult(cameraIntent, CAMERA_REQUEST);
//        }
//    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //((ImageView)findViewById(R.id.photo)).setImageBitmap(photo);
            imageView.setImageBitmap(photo);
        }
    }
}
