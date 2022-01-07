package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EnterRoom extends AppCompatActivity implements IImageModified{

    Thread checkWinningStatus;
    private int pairs = 0;
    RoomAdapter adapter;
    // create list to capture images that are correctly selected
    private List<Integer> selectedPositions = new ArrayList<>();
    private HashMap<Integer, ImageModel> counter = new HashMap<Integer, ImageModel>();
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
    public void checkImage(ImageView image, int position) {
        check(image, position);
    }

    // check and add to number of correctly opened items
    // close the wrongly selected ones
    @SuppressLint("ResourceType")
    private void check(ImageView imageview, int position){
        // disable user from clicking previously selected positions
        selectedPositions.add(position);

        Integer resourceid = imageview.getId();
        imageview.setImageResource(resourceid);

        if(counter.isEmpty()){
            ImageModel model = new ImageModel(imageview, position);
            counter.put(resourceid, model);
        }else{

            // compare the values
            if(counter.containsKey(resourceid.intValue())){
                // both pictures are similar and we do not close them
                // add to winning pairs
                imageview.setClickable(false);
                counter.get(resourceid.intValue()).getImageView().setClickable(false);
                pairs++;
            }else{
                // close the 2 pictures as they are dissimilar
                // create a delay of 1 sec before closing
                Collection<ImageModel> val = counter.values();
                ImageView imageView2 = null;
                int position2 = -1;
                for(ImageModel i : val){
                    imageView2 = i.getImageView();
                    position2 = i.getPosition();
                }
                try{
                    selectedPositions.remove(position);
                    selectedPositions.remove(position2);
                }catch(Exception e){
                    e.printStackTrace();
                }
                closeImages(imageview, imageView2);
            }
            counter.clear();
        }
    }

    public void closeImages(ImageView image1, ImageView image2){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image1.setImageResource(R.drawable.question);
                        image2.setImageResource(R.drawable.question);
                    }
                });
            }
        }).start();

    }
}