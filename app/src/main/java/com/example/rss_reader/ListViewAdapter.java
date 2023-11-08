package com.example.rss_reader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<VNEXPRESS> {
    Activity activity;
    int idlayout;
    ArrayList<VNEXPRESS> list;


    public ListViewAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<VNEXPRESS> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.idlayout = resource;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(idlayout,null);
        VNEXPRESS item = list.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.txtTitle);
        title.setText(item.getTitle());
        TextView des = (TextView) convertView.findViewById(R.id.txtDes);
        des.setText(item.getDescription());
        TextView pub = (TextView) convertView.findViewById(R.id.pubDate);
        pub.setText(item.getPubDate());
        return convertView;
    }
}
