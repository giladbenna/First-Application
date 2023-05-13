package com.example.myapplication.Models;

import com.google.android.gms.maps.model.LatLng;

public class ScoreItem {

    private int score;
    private String name = "";
    private String image = "";
    private LatLng latLng;

    public ScoreItem(){}

    public String getName() {
        return name;
    }

    public ScoreItem setName(String name) {
        this.name = name;
        return this;
    }
    public LatLng getLatLng() {
        return latLng;
    }

    public ScoreItem setLatLng(LatLng latLng) {
        this.latLng = latLng;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ScoreItem setImage(String image) {
        this.image = image;
        return this;
    }

    public int getScore() {
        return score;
    }

    public ScoreItem setScore(int score) {
        this.score = score;
        return this;
    }
}
