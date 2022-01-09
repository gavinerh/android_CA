package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EnterRoom extends AppCompatActivity implements IImageModified{

    TextView winningStatus;
    Thread checkWinningStatus;
    private int pairs = 0;
    RoomAdapter adapter;
    TextView timer;
    // create list to capture images that are correctly selected
    private List<Integer> selectedPositions = new ArrayList<>();
    // stores filename as key, and image model as values
    private HashMap<String, ImageModel> counter = new HashMap<String, ImageModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_room);

        timer = findViewById(R.id.countdownTimer);
        winningStatus = findViewById(R.id.winning_status);
        setWinningStatus();
        Intent intent = getIntent();
        ArrayList<String> images = intent.getStringArrayListExtra("testing");

        adapter = new RoomAdapter(this, images, this);
        GridView gridView = (GridView) findViewById(R.id.gridView2);
        gridView.setAdapter(adapter);

        timerControl();

    }

    private void timerControl(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                long durationInMins = 1;
                long durationInSec = durationInMins * 60;
                while(durationInSec > 0){
                    durationInSec -= 1;
                    long timeToDisplay = durationInSec;
                    long secsToDisplay = (timeToDisplay) % 60;
                    long minsToDisplay = (timeToDisplay) / 60;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText(String.format("%02d : %02d", minsToDisplay, secsToDisplay));
                        }
                    });

                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                if(pairs < 6){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), GameOutcome.class);
                            intent.putExtra("outcome", "lost");
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        }).start();
    }

    private void setWinningStatus(){
        String str = String.format("%s", pairs);
        str += " of 6 matches";
        winningStatus.setText(str);
    }

    @Override
    public void checkImage(ImageView image, String[] playinglist) {
        check(image, playinglist);
    }

    // check and add to number of correctly opened items
    // close the wrongly selected ones
    @SuppressLint("ResourceType")
    private void check(ImageView imageview, String[] playingList){
        // disable user from clicking previously selected positions
        // id holds the position of the image in grid
        selectedPositions.add(imageview.getId());

        Integer position = imageview.getId();
        Bitmap bitmap = BitmapFactory.decodeFile(playingList[imageview.getId()]);
        imageview.setImageBitmap(bitmap);

        if(counter.isEmpty()){
            ImageModel model = new ImageModel(imageview, position);
            counter.put(playingList[imageview.getId()], model);
        }else{

            // compare the values by comparing the filenames
            if(counter.containsKey(playingList[imageview.getId()])){
                // both pictures are similar and we do not close them
                // add to winning pairs
                imageview.setClickable(false);
                counter.get(playingList[imageview.getId()]).getImageView().setClickable(false);
                pairs++;
                setWinningStatus();
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
        Log.d("Winning pairs", String.format("%s", pairs));
        if(pairs == 6){
            Intent intent = new Intent(this, GameOutcome.class);
            intent.putExtra("outcome", "won");
            startActivity(intent);
            finish();
        }
    }

    // when the two images are not similar, close them
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