package com.example.myapplication.Models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.myapplication.R;

public class OpeningPage extends AppCompatActivity {

    private final MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_page_activity);
        Buttons();

    }

    private void Buttons(){
        Button arrowModeButton = findViewById(R.id.arrowModeButton);

        arrowModeButton.setOnClickListener(v -> {
            mainActivity.ifArrowsMode = true;
            Intent intent = new Intent(OpeningPage.this, MainActivity.class);
            startActivity(intent);
        });

        Button sensorModeButton = findViewById(R.id.sensorModeButton);

        sensorModeButton.setOnClickListener(v -> {
            mainActivity.ifArrowsMode = false;
            Intent intent = new Intent(OpeningPage.this, MainActivity.class);
            startActivity(intent);
        });
    }

}
