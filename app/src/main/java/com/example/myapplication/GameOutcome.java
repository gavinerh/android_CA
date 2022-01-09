package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOutcome extends AppCompatActivity {

    TextView textView;
    Button playAgain;
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

        playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startService(intent);
                finish();
            }
        });
    }
}