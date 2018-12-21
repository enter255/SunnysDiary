package com.example.main;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class diary extends AppCompatActivity {
    ImageView imageView1;
    TextView textView;

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
        int i=0;
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
                if (image == null) {
                    Toast.makeText(this, "Diary- NO IMAGE", Toast.LENGTH_SHORT).show();
                }
                bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                //imageView1.setImageBitmap(bmp);
                //textView.setText(Integer.toString(idx));

                adapter.addItem(bmp,
                        "Box", Integer.toString(i)) ;
            }
            c.close();

        } catch (Exception e) {
            Log.e("Error : ", e.getMessage());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        if (db != null) {
            db.close();
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String titleStr = item.getTitle();
                String descStr = item.getDesc();
                Bitmap bmp = item.getPic();

                // TODO : use item data.
            }
        });
    }

    protected void onResume() {
        super.onResume();
        //DBtest();
    }

    public Bitmap DBtest() {
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
                if (image == null) {
                    Toast.makeText(this, "Diary- NO IMAGE", Toast.LENGTH_SHORT).show();
                }
                bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                //imageView1.setImageBitmap(bmp);
                //textView.setText(Integer.toString(idx));
            }
            c.close();
            return bmp;
        } catch (Exception e) {
            Log.e("Error : ", e.getMessage());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        if (db != null) {
            db.close();
        }
        return null;
    }
}
