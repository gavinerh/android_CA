package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// links between the grid card item and the coursemodel
public class MyAdapter extends BaseAdapter {
    private Context context;

    public MyAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return carIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(context);
            // set the imageView look
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(carIds[position]);
        // we set the id of imageView to the image resource id which is linked to the photo
        imageView.setId(carIds[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this toast displays the image resource id after being pressed
                Toast.makeText(context.getApplicationContext(), String.format("%s", imageView.getId()), Toast.LENGTH_SHORT).show();
            }
        });
        return imageView;
    }

    public Integer[] carIds = {
            R.drawable.sample1, R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5, R.drawable.sample6,
            R.drawable.sample7, R.drawable.sample8, R.drawable.sample9,
            R.drawable.sample10
    };
}
