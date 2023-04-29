package com.example.myapplication.Models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.myapplication.R;
import com.example.myapplication.ScoreBoardPage;

public class OpeningPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_page_activity);
        Buttons();

    }

    private void Buttons(){
        Button arrowModeButton = findViewById(R.id.arrowModeButton);

        arrowModeButton.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningPage.this, MainActivity.class);
            intent.putExtra("key", true);
            startActivity(intent);
        });

        Button sensorModeButton = findViewById(R.id.sensorModeButton);

        sensorModeButton.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningPage.this, MainActivity.class);
            intent.putExtra("key", false);
            startActivity(intent);
        });

        Button scoreBoardButton = findViewById(R.id.scoreBoardButton);

        scoreBoardButton.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningPage.this, ScoreBoardPage.class);
            startActivity(intent);
        });
    }

}
