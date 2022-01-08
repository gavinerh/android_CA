package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity implements IImageClicked {

    String[] filenames;
    MyAdapter myAdapter;
    GridView gridView;
    EditText enterUrl;
    Button searchBtn;
    List<ImageView> imageViewList;
    // extracted url of all img tags in entered url , more than 20 images
    List<String> imageUrlExtracted = new ArrayList<>();
    // store the key as filename, value as url, exactly 20 pairs
    HashMap<String, String> storedImageUrl = new HashMap<String, String>();
    // key is the position and string is the filename
    HashMap<Integer, String> gridImageDescriptors = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFilenames();
        // gotten back the list of imageview after initUIElements, where the gridview is initialised
        initUIElements();
        // download images
//        downloadImagesHandler();
    }

    @Override
    public void selectedImagesList(ArrayList<Integer> list) {
        // receive the list of grid positions entered, check again if correct

        // use the gridImageDescriptor to create a new list of filenames to send over
        Intent intent = new Intent(this, EnterRoom.class);
        intent.putExtra("testing", list);
        startActivity(intent);
    }

    private void initUIElements() {
        myAdapter = new MyAdapter(this, this, filenames);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(myAdapter);
        searchBtn = findViewById(R.id.search);
        enterUrl = findViewById(R.id.enter_url);
        imageViewList = myAdapter.getImageViewList();
    }

    private void initFilenames() {
        filenames = new String[20];
        for (int i = 0; i < filenames.length; i++) {
            String filename = "sample";
            filename += String.valueOf(i + 1);
            filenames[i] = filename;
        }
    }

    public void search(View view){
        String url = enterUrl.getText().toString();
        downloadImagesHandler(url);
    }

    private void downloadImagesHandler(String url) {
        if(url == null || url.equals("")) return;
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
                    ImageView imgView = imageViewList.get(i+2); // dont know why need to plus 2 but it works
                    int position = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
                            // get imageview from list initialised in the myAdapter class
                            imgView.setImageBitmap(bitmap);
                            gridImageDescriptors.put(position, destFile.getAbsolutePath());
                        }
                    });

                }
            }
        }).start();
    }

    private void getImageUrl(String strURL) {
//        String strURL = "https://www.stocksnap.io";

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

    private void populateStoredImageUrl() {
        for (int i = 0; i < filenames.length; i++) {
            String imageUrl = imageUrlExtracted.remove(i);
            storedImageUrl.put(filenames[i], imageUrl);
        }
    }
}