package com.example.myapplication;

import android.widget.ImageView;

public class ImageModel {
    private ImageView imageView;
    private int position;

    public ImageModel(ImageView image, int position){
        this.imageView = image;
        this.position = position;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
