package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Adapters.ScoreBoardAdapter;
import com.example.myapplication.Utilities.DataManager;

public class ScoreBoardPage extends AppCompatActivity {


    private RecyclerView main_LST_scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_adapter);

        findViews();
        initViews();
    }


    private void initViews() {
        ScoreBoardAdapter scoreBoardAdapterAdapter = new ScoreBoardAdapter(this, DataManager.getScores());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        main_LST_scores.setAdapter(scoreBoardAdapterAdapter);
    }

    private void findViews() {
        main_LST_scores = findViewById(R.id.main_LST_scores);
    }
}
