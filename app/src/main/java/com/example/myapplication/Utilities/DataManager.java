package com.example.myapplication.Utilities;

import com.example.myapplication.Logic.GameManager;
import com.example.myapplication.Models.ScoreItem;

import java.util.ArrayList;

public class DataManager {
    private static final GameManager gameManager = new GameManager();

    public static ArrayList<ScoreItem> getScores() {
        ArrayList<ScoreItem> scores = new ArrayList<>();

        scores.add(new ScoreItem()
                .setScore(gameManager.getOdometerScore())
                .setName("Gilad")
                .setImage("https://image.tmdb.org/t/p/w600_and_h900_bestv2/wdE6ewaKZHr62bLqCn7A2DiGShm.jpg"));

        scores.add(new ScoreItem()
                .setScore(1500)
                .setName("David")
                .setImage("https://image.tmdb.org/t/p/w600_and_h900_bestv2/wdE6ewaKZHr62bLqCn7A2DiGShm.jpg"));

        scores.add(new ScoreItem()
                .setScore(50)
                .setName("Or")
                .setImage("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/vdVab7yNvgYEMd8shCfy2D6nTMu.jpg"));

        return scores;

    }
}