package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    String[] filenames;
    ProgressBar progressBar;
    EditText enterUrl;
    Button searchBtn;
    // contains reference to all the image view
    List<ImageView> imageViewList = null;
    // extracted url string from Jsoup
    List<String> imageUrlExtracted = null;

    List<Integer> storedImageView = null;
    // store the key as filename, value as url string, for referencing filename to url string, only 20
    HashMap<String, String> storedImageUrl = null;
    // key is the integer grid position and string is the filepath
    HashMap<Integer, String> gridImageToFilepath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDataStructures();
        // initialise the filenames
        initFilenames();
        initUIElements();
    }


    private void initDataStructures(){
        storedImageUrl = new HashMap<String, String>();
        storedImageView = new ArrayList<>();
        imageUrlExtracted = new ArrayList<>();
        imageViewList = new ArrayList<>();
        gridImageToFilepath = new HashMap<>();
    }

    private void initUIElements() {
        searchBtn = findViewById(R.id.search);
        enterUrl = findViewById(R.id.enter_url);
        progressBar = findViewById(R.id.progress_bar);
        populateImageViewList();
    }

    // fill imageViewlist with the imageViews
    private void populateImageViewList(){
        imageViewList.add(findViewById(R.id.image1));
        imageViewList.add(findViewById(R.id.image2));
        imageViewList.add(findViewById(R.id.image3));
        imageViewList.add(findViewById(R.id.image4));
        imageViewList.add(findViewById(R.id.image5));
        imageViewList.add(findViewById(R.id.image6));
        imageViewList.add(findViewById(R.id.image7));
        imageViewList.add(findViewById(R.id.image8));
        imageViewList.add(findViewById(R.id.image9));
        imageViewList.add(findViewById(R.id.image10));
        imageViewList.add(findViewById(R.id.image11));
        imageViewList.add(findViewById(R.id.image12));
        imageViewList.add(findViewById(R.id.image13));
        imageViewList.add(findViewById(R.id.image14));
        imageViewList.add(findViewById(R.id.image15));
        imageViewList.add(findViewById(R.id.image16));
        imageViewList.add(findViewById(R.id.image17));
        imageViewList.add(findViewById(R.id.image18));
        imageViewList.add(findViewById(R.id.image19));
        imageViewList.add(findViewById(R.id.image20));
        for(int i=0; i<imageViewList.size(); i++){
            imageViewList.get(i).setImageResource(R.drawable.question);
            int position = i;
            imageViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send to a method to store the imageView that is selected
                    tabulateSelectedImageView(position);
                }
            });
        }
    }

    // combine all the images selected into a list
    private void tabulateSelectedImageView(int position){
        if(storedImageView.contains(position)){
            Toast.makeText(getApplicationContext(), "Added this image already", Toast.LENGTH_SHORT).show();
        }else{
            storedImageView.add(position);
        }
        if(storedImageView.size() == 6){
            Intent intent = new Intent(this, EnterRoom.class);
            ArrayList<String> copy = new ArrayList<>();
            for(Integer i : storedImageView){
                copy.add(gridImageToFilepath.get(i));
            }
            intent.putExtra("testing", copy);
            startActivity(intent);
        }
    }

    private void initFilenames() {
        filenames = new String[20];
        for (int i = 0; i < filenames.length; i++) {
            String filename = "sample";
            filename += String.valueOf(i + 1);
            filenames[i] = filename;
        }
    }
    // onclick method for search button
    public void search(View view){
        String url = enterUrl.getText().toString();
        downloadImagesHandler(url);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    // handle downloading of images
    private void downloadImagesHandler(String url) {
        if(url == null || url.equals("")) return;
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // populate the list with image url
                getImageUrl(url);

                // select 20 images from arraylist and store in hashmap
                populateStoredImageUrl();

                // download the images from image url stored in list
                File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                for (int i=0; i<filenames.length; i++) {
                    File destFile = new File(dir, filenames[i]);
                    String imgUrl = storedImageUrl.get(filenames[i]);
                    ImageDownloader.downloadImage(imgUrl, destFile);
                    ImageView imgView = imageViewList.get(i);
                    int position = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
                            // get imageview from list initialised in the myAdapter class
                            imgView.setImageResource(0);
                            imgView.setImageBitmap(bitmap);
                            progressBar.incrementProgressBy(5);
                            gridImageToFilepath.put(position, destFile.getAbsolutePath());
                        }
                    });
                }
            }
        }).start();
    }
    // extract image url from website using Jsoup
    private void getImageUrl(String strURL) {

        //connect to the website and get the document

        try {
            Document document = Jsoup
                    .connect(strURL)
                    .get();
            //select all img tags
            Elements imageElements = document.select("img");

            //iterate over each image
            for (Element imageElement : imageElements) {
                //make sure to get the absolute URL using abs: prefix
                String strImageURL = imageElement.attr("abs:src");
                if (strImageURL.contains("svg")) continue;
                imageUrlExtracted.add(strImageURL);
            }
        } catch (IOException e) {
            Log.d("downloading images failed", "Image downloader from getImageUrl failed");
            e.printStackTrace();
        }
    }
    // store filename (key) and image url (value) in hashmap
    private void populateStoredImageUrl() {
        for (int i = 0; i < filenames.length; i++) {
            String imageUrl = imageUrlExtracted.remove(i);
            storedImageUrl.put(filenames[i], imageUrl);
        }
    }
}