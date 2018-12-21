package com.example.main;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_view_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView pictureImageView = (ImageView) convertView.findViewById(R.id._picture);
        TextView datetimeTextView = (TextView) convertView.findViewById(R.id._datetime);
        TextView locationTextView = (TextView) convertView.findViewById(R.id._location);
        TextView contextTextView = (TextView) convertView.findViewById(R.id._context);
        ImageView emotionImageView = (ImageView)convertView.findViewById(R.id._emotion);
        ImageView whetherImageView = (ImageView)convertView.findViewById(R.id._whether);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        pictureImageView.setImageBitmap(listViewItem.getPicture());
        datetimeTextView.setText(listViewItem.getDatetime());
        locationTextView.setText(listViewItem.getLocation());
        contextTextView.setText(listViewItem.getContext());
        String s_emotion = listViewItem.getEmotion();
        String s_whether = listViewItem.getWhether();
        if (s_emotion.equals("happy")) {
            emotionImageView.setImageResource(R.drawable.happy);
        } else if (s_emotion.equals("inlove")) {
            emotionImageView.setImageResource(R.drawable.inlove);
        } else if (s_emotion.equals("smile")) {
            emotionImageView.setImageResource(R.drawable.smile);
        } else if (s_emotion.equals("thinking")) {
            emotionImageView.setImageResource(R.drawable.thinking);
        } else if (s_emotion.equals("unhappy")) {
            emotionImageView.setImageResource(R.drawable.unhappy);
        }

        if (s_whether.equals("sunny")) {
            whetherImageView.setImageResource(R.drawable.sunny);
        } else if (s_whether.equals("clouds")) {
            whetherImageView.setImageResource(R.drawable.clouds);
        } else if (s_whether.equals("rainy")) {
            whetherImageView.setImageResource(R.drawable.rainy);
        } else if (s_whether.equals("snowflake")) {
            whetherImageView.setImageResource(R.drawable.snowflake);
        } else if (s_whether.equals("thunder")) {
            whetherImageView.setImageResource(R.drawable.thunder);
        }


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Bitmap picture, String datetime, String location, String context, String emotion, String whether, int idx) {
        ListViewItem item = new ListViewItem();
        item.setPicture(picture);
        item.setDatetime(datetime);
        item.setLocation(location);
        item.setContext(context);
        item.setEmotion(emotion);
        item.setWhether(whether);
        listViewItemList.add(item);
    }
}
