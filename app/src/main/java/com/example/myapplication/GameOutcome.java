package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameOutcome extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_message);

        textView = findViewById(R.id.outcomeMessage);
        Intent intent = getIntent();
        String messsage = intent.getStringExtra("outcome");
        if(messsage.equals("lost")){
            textView.setText("You have ran out of time");
        }else{
            textView.setText("Congratulations you won");
        }
    }
}