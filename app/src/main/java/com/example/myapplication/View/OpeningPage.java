package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.R;

public class OpeningPage extends AppCompatActivity {
    private String playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_page_activity);
        playerName = getIntent().getStringExtra("playerName");
        Buttons();

    }

    private void Buttons(){
        Button arrowModeButtonSlow = findViewById(R.id.arrowModeButtonSlow);

        arrowModeButtonSlow.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningPage.this, MainActivity.class);
            intent.putExtra("key", true);
            intent.putExtra("fast", false);
            intent.putExtra("playerName", playerName);
            startActivity(intent);
        });

        Button arrowModeButtonFast = findViewById(R.id.arrowModeButtonFast);

        arrowModeButtonFast.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningPage.this, MainActivity.class);
            intent.putExtra("key", true);
            intent.putExtra("fast", true);
            intent.putExtra("playerName", playerName);
            startActivity(intent);
        });

        Button sensorModeButton = findViewById(R.id.sensorModeButton);

        sensorModeButton.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningPage.this, MainActivity.class);
            intent.putExtra("key", false);
            intent.putExtra("playerName", playerName);
            startActivity(intent);
        });

        Button scoreBoardButton = findViewById(R.id.scoreBoardButton);

        scoreBoardButton.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningPage.this, ScoreBoardActivity.class);
            startActivity(intent);
        });
    }

}
