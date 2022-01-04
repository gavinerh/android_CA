package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAdapter = new MyAdapter(this);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(myAdapter);




//        gridView = findViewById(R.id.gridView);

        // data to be displayed into gridview
//        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();
//        for(int i=0; i<20; i++){
//            courseModelArrayList.add(new CourseModel(R.drawable.hello));
//        }

        // Attach the adaptor
//        MyAdapter myAdapter = new MyAdapter(this, courseModelArrayList);
//        gridView.setAdapter(myAdapter);

    }
}