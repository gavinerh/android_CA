package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EnterRoom extends AppCompatActivity implements IImageModified{

    private int pairs = 0;
    ImageView imageViewTest;
    RoomAdapter adapter;
    private HashMap<Integer, ImageView> counter = new HashMap<Integer, ImageView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_room);

        Intent intent = getIntent();
        ArrayList<Integer> images = intent.getIntegerArrayListExtra("testing");

        adapter = new RoomAdapter(this, images, this);
        GridView gridView = (GridView) findViewById(R.id.gridView2);
        gridView.setAdapter(adapter);


    }

    @Override
    public void checkImage(ImageView image) {
        check(image);
    }

    // check and add to number of correctly opened items
    // close the wrongly selected ones
    @SuppressLint("ResourceType")
    private void check(ImageView imageview){
        Integer resourceid = imageview.getId();
        imageview.setImageResource(resourceid);

        if(counter.isEmpty()){
            counter.put(resourceid, imageview);
        }else{
            // compare the values
            if(counter.containsKey(resourceid.intValue())){
                // both pictures are similar and we do not close them
                // add to winning pairs
                pairs++;
            }else{
                // close the 2 pictures as they are dissimilar
                // create a delay of 1 sec before closing

                Collection<ImageView> val = counter.values();
                ImageView imageView2 = null;
                for(ImageView i : val){
                    imageView2 = i;
                }
                adapter.closeImages(imageview, imageView2);
            }
            counter.clear();
        }
    }
}