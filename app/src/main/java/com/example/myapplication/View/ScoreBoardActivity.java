package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.Adapters.ScoreBoardAdapter;
import com.example.myapplication.Models.ScoreItem;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.DataManager;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ScoreBoardActivity extends AppCompatActivity {


    private RecyclerView main_LST_scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_adapter);

        findViews();
        initViews();
        returnButton();

    }


    private void initViews() {
        ScoreBoardAdapter scoreBoardAdapterAdapter = new ScoreBoardAdapter(this,DataManager.getInstance().readArrayScoresFromSP());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        main_LST_scores.setAdapter(scoreBoardAdapterAdapter);
    }
    private void returnButton(){
        Button returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScoreBoardActivity.this, OpeningPage.class);
            startActivity(intent);
        });
    }


    private void findViews() {
        main_LST_scores = findViewById(R.id.main_LST_scores);
    }
}
