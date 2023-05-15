package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.Adapters.ScoreBoardAdapter;
import com.example.myapplication.MapFragment;
import com.example.myapplication.Models.ScoreItem;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.DataManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ScoreBoardActivity extends AppCompatActivity {

    private String playerName;
    private RecyclerView main_LST_scores;
    private  boolean isFast = false;
    private  boolean ifArrows = false;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_adapter);
        Intent intent = getIntent();
        ifArrows = intent.getBooleanExtra("key", false); // or true
        isFast = intent.getBooleanExtra("fast", false); // or true
        playerName = intent.getStringExtra("playerName");
        location = getIntent().getParcelableExtra("location");

        findViews();
        initViews();
        returnButton();
        initMap(new LatLng(location.getLatitude(),location.getLongitude()));
    }

    private void initMap(LatLng latLng){
        //initialize fragment
        Fragment fragment = new MapFragment(latLng);


        //open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();

    }


    private void initViews() {
        ScoreBoardAdapter scoreBoardAdapterAdapter = new ScoreBoardAdapter(this,DataManager.getInstance().readArrayScoresFromSP());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        main_LST_scores.setAdapter(scoreBoardAdapterAdapter);

        scoreBoardAdapterAdapter.setOnClickListener(new ScoreBoardAdapter.OnClickListener() {
            @Override
            public void onClick(int position, ScoreItem scoreItem) {
                // Passing the data to the
                // EmployeeDetails Activity
                initMap(scoreItem.getLatLng());
            }
        });
    }
    private void returnButton(){
        Button returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScoreBoardActivity.this, OpeningPage.class);
            intent.putExtra("playerName", playerName);
            intent.putExtra("location", location);
            startActivity(intent);
        });

        Button startGameAgain = findViewById(R.id.startGameAgain);

        startGameAgain.setOnClickListener(v -> {
            Intent intent = new Intent(ScoreBoardActivity.this, MainActivity.class);
            intent.putExtra("playerName", playerName);
            intent.putExtra("key", ifArrows);
            intent.putExtra("fast", isFast);
            intent.putExtra("location", location);
            startActivity(intent);
        });
    }




    private void findViews() {
        main_LST_scores = findViewById(R.id.main_LST_scores);
    }
}
