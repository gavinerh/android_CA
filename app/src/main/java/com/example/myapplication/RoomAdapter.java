package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class RoomAdapter extends BaseAdapter implements IImageModified {
    private Context context;
    private IImageModified imageModified;


    // create list to hold images list for playing
    // list position will correspond to image position on the grid
    private Integer[] playingList;

    // constructor
    public RoomAdapter(Context context, ArrayList<Integer> list, IImageModified imageModified){
        this.imageModified = imageModified;
        playingList = Randomize.randomizeArray(list);
        for(int i=0; i<playingList.length; i++){
            Log.d("Testing purposes", String.format("%s", playingList[i]));
        }
        this.context = context;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(context);
            // set the imageView look
            imageView.setClickable(true);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
        }else {
            imageView = (ImageView) convertView;
        }
//        imageView.setImageResource(R.drawable.question);
        imageView.setImageResource(R.drawable.question);
        // we set the id of imageView to the image resource id which is linked to the photo
        imageView.setId(playingList[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send to method check

                checkImage(imageView, position);
            }
        });
        return imageView;
    }



    public void closeImages(ImageView image1, ImageView image2){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        image1.setImageResource(R.drawable.question);
        image2.setImageResource(R.drawable.question);
    }

    @Override
    public int getCount() {

        return playingList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public void checkImage(ImageView image, int position) {
        imageModified.checkImage(image, position);
    }
}
