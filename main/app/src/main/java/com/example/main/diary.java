package com.example.main;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class diary extends AppCompatActivity {
    ImageView photo;
    TextView datetime;
    TextView location;
    ImageView emotion;
    ImageView whether;
    TextView context;
    DiaryData diaryData;
    String s_emotion;
    String s_whether;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ListView listview;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        int i = 0;
        // 첫 번째 아이템 추가.
        SQLiteDatabase db = null;
        Bitmap bmp = null;
        try {
            db = openOrCreateDatabase("picdiary.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            db.execSQL("create table if not exists diary (_id INTEGER PRIMARY KEY AUTOINCREMENT, a TEXT, b INTEGER);");
            Cursor c = null;
            c = db.rawQuery("Select * from diary;", null);
            startManagingCursor(c);

            Toast.makeText(this, "Diary- DATA", Toast.LENGTH_SHORT).show();
            while (c.moveToNext()) {
                byte[] image = null;
                int idx = c.getInt(0);
                image = c.getBlob(1);
                String datetime = c.getString(2);
                String location = c.getString(3);
                String context = c.getString(4);
                String emotion = c.getString(5);
                String whether = c.getString(6);
                if (image == null) {
                    Toast.makeText(this, "Diary- NO IMAGE", Toast.LENGTH_SHORT).show();
                }
                bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                //imageView1.setImageBitmap(bmp);
                //textView.setText(Integer.toString(idx));

                adapter.addItem(bmp,datetime,location,context,emotion,whether, idx);
            }
            c.close();

        } catch (Exception e) {
            Log.e("Error : ", e.getMessage());
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        if (db != null) {
            db.close();
        }

    }
//        super.onCreate(savedInstanceState);
//        diaryData = new DiaryData();
//        setContentView(R.layout.activity_diary);
//        photo = findViewById(R.id.photo);
//        datetime = findViewById(R.id.datetime);
//        location = findViewById(R.id.location);
//        emotion = findViewById(R.id.emotion);
//        whether = findViewById(R.id.whether);
//        context = findViewById(R.id.context);
//        Toast.makeText(this, "Diary", Toast.LENGTH_SHORT).show();
//        DBtest();
//    }

    protected void onResume() {
        super.onResume();
        DBtest();
    }

    public void DBtest() {
        SQLiteDatabase db = null;
        try {
            db = openOrCreateDatabase("picdiary.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            db.execSQL("create table if not exists diary(_id INTEGER PRIMARY KEY AUTOINCREMENT, pic BLOB,datetime String,location String,context String,emotion String,whether String);");
            Cursor c = null;
            c = db.rawQuery("Select * from diary;", null);
            startManagingCursor(c);

            Toast.makeText(this, "Diary- DATA", Toast.LENGTH_SHORT).show();
            while (c.moveToNext()) {
                byte[] image = null;
                //int idx = c.getInt(0);
                image = c.getBlob(1);
                if (image == null) {
                    Toast.makeText(this, "Diary- NO IMAGE", Toast.LENGTH_SHORT).show();
                }
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length); //
                photo.setImageBitmap(bmp);
                //textView.setText(Integer.toString(idx));

                datetime.setText(c.getString(2));
                location.setText(c.getString(3));
                context.setText(c.getString(4));
                s_emotion = c.getString(5);
                s_whether = c.getString(6);

                if (s_emotion.equals("happy")) {
                    emotion.setImageResource(R.drawable.happy);
                } else if (s_emotion.equals("inlove")) {
                    emotion.setImageResource(R.drawable.inlove);

                } else if (s_emotion.equals("smile")) {
                    emotion.setImageResource(R.drawable.smile);

                } else if (s_emotion.equals("thinking")) {
                    emotion.setImageResource(R.drawable.thinking);

                } else if (s_emotion.equals("unhappy")) {
                    emotion.setImageResource(R.drawable.unhappy);

                }
            }
            c.close();
        } catch (Exception e) {
            Log.e("Error : ", e.getMessage());
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        if (db != null) {
            db.close();
        }
    }
}