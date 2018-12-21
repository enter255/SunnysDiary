package com.example.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class getPicture extends AppCompatActivity{
    private final int GALLERY_CODE = 1112;
    public Bitmap image_bitmap;
    RadioGroup emotion_group, whether_group;
    ImageView emotion_view, whether_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_CODE);

        //** get emotion icon **
        emotion_view = findViewById(R.id.emotion_view);
        emotion_group = findViewById(R.id.emotion);
        emotion_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.happy)
                    emotion_view.setImageResource(R.drawable.happy);
                else if(checkedId == R.id.inlove)
                    emotion_view.setImageResource(R.drawable.inlove);
                else if(checkedId == R.id.smile)
                    emotion_view.setImageResource(R.drawable.smile);
                else if(checkedId == R.id.thinking)
                    emotion_view.setImageResource(R.drawable.thinking);
                else if(checkedId == R.id.unhappy)
                    emotion_view.setImageResource(R.drawable.unhappy);
            }
        });

        //** get whether icon **
        whether_view = findViewById(R.id.whether_view);
        whether_group = findViewById(R.id.whether);
        whether_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.sunny)
                    whether_view.setImageResource(R.drawable.sunny);
                else if(checkedId == R.id.clouds)
                    whether_view.setImageResource(R.drawable.clouds);
                else if(checkedId == R.id.rainy)
                    whether_view.setImageResource(R.drawable.rainy);
                else if(checkedId == R.id.thunder)
                    whether_view.setImageResource(R.drawable.thunder);
                else if(checkedId == R.id.snowflake)
                    whether_view.setImageResource(R.drawable.snowflake);
            }
        });
    }

    private void selectGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    //String name_Str = getImageNameToUri(data.getData());
                    //이미지 데이터를 비트맵으로 받아온다.
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView) findViewById(R.id.user_image);
                    //배치해놓은 ImageView에 set
                    image.setImageBitmap(image_bitmap);

                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();
                    //HERE

                    Uri uri = data.getData();
                    String uri_path = getRealPathFromURI(uri);
                    Toast.makeText(this, uri_path, Toast.LENGTH_SHORT).show();
                    try {
                        ExifInterface exif = new ExifInterface(uri_path);
                        showExif(exif);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
                    }

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }
    }

    //get date and time info from photo
    private void showExif(ExifInterface exif) {
        String myAttribute = "";
        TextView day_time = findViewById(R.id.day_time);

        myAttribute += getTagString(ExifInterface.TAG_DATETIME, exif);
        day_time.setText("   "+myAttribute);
    }

    private String getRealPathFromURI(Uri contentURI) {

        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }
    public String getImageNameToUri(Uri data){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        return imgName;
    }

    public void save(View view){
        //deleteDatabase("picdiary.db");
        SQLiteDatabase db=null;
        try {
            db = openOrCreateDatabase("picdiary.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            db.execSQL("create table if not exists diary (_id INTEGER PRIMARY KEY AUTOINCREMENT, pic BLOB);");
            System.out.println("Save button click...");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] data = stream.toByteArray();
            SQLiteStatement p = db.compileStatement("insert into diary(pic) values(?);");
            p.bindBlob(1,data);
            p.execute();
            // String sql = "insert into diary(a,b) values('temporary data', 2);";
            //db.execSQL(sql);
            finish();
        }catch(Exception e){
            Log.i("ERROR : ",e.getMessage());
            e.printStackTrace();
        }
        System.out.println("error?");


        Toast.makeText(this, "insert okay", Toast.LENGTH_SHORT).show();
    }
    private String getTagString(String tag, ExifInterface exif) {
        return (tag + " : " + exif.getAttribute(tag) + "\n");
    }
}
