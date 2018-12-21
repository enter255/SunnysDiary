package com.example.main;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Album extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toast.makeText(this, "Album", Toast.LENGTH_SHORT).show();
        DBtest();
    }

    protected void onResume() {
        super.onResume();
        //DBtest();
    }

    public void DBtest() {
        SQLiteDatabase db = null;
        try {
            db = openOrCreateDatabase("picdiary.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            Cursor c = db.rawQuery("Select * from diary;", null);
            startManagingCursor(c);

            ListAdapter adapt = new SimpleCursorAdapter(
                    this, android.R.layout.simple_list_item_2, c, new String[]{"a", "b"},
                    new int[]{android.R.id.text1, android.R.id.text2}, 0
            );
            Toast.makeText(this, "DB", Toast.LENGTH_SHORT).show();
            setListAdapter(adapt);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (db != null) {
            db.close();
        }
    }
}