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
import java.util.List;

// links between the grid card item and the coursemodel
public class MyAdapter extends BaseAdapter implements IImageClicked {
    private Context context;
    private ArrayList<Integer> selectedImages = new ArrayList<>();
    private String[] filenames;
    private IImageClicked imageClicked;
    // contain a list of imageView in order from 1 to 20
    private List<ImageView> imageViewList = new ArrayList<>();
    public MyAdapter(Context c, IImageClicked imageClicked, String[] filenames) {
        this.filenames = filenames;
        this.imageClicked = imageClicked;
        context = c;
    }

    public void addToSelectedImages(int id){
        if(selectedImages.size() <= 6 && !selectedImages.contains(id)){
            selectedImages.add(id);
        }else{
            Toast.makeText(context.getApplicationContext(), "Exceeded the count of 6 images or already contain image", Toast.LENGTH_SHORT).show();
        }
        if(selectedImages.size() == 6){
            ArrayList<Integer> copy = new ArrayList<>();
            for(Integer i : selectedImages){
                copy.add(i);
            }
            // clear the current list of previously selected images
            // in case user clicks back button
            selectedImages = new ArrayList<>();
            selectedImagesList(copy);
        }
    }

    @Override
    public int getCount() {
        return filenames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public List<Integer> getSelectedImages(){
        return selectedImages;
    }

    public List<ImageView> getImageViewList(){
        return imageViewList;
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
        imageView.setImageResource(R.drawable.question);
        // we set the id of imageView to the image resource id which is linked to the photo
        imageView.setId(position);
        imageViewList.add(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), String.format("%s", position), Toast.LENGTH_SHORT).show();
                // add the selected images into the list
                addToSelectedImages(imageView.getId());
            }
        });

        return imageView;
    }


    @Override
    public void selectedImagesList(ArrayList<Integer> list) {
        imageClicked.selectedImagesList(list);
    }
}
