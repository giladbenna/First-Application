package com.example.myapplication.Models;

public class ScoreItem {

    private int score;
    private String name = "";
    private String image = "";

    public ScoreItem(){}

    public String getName() {
        return name;
    }

    public ScoreItem setName(String name) {
        this.name = name;
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
