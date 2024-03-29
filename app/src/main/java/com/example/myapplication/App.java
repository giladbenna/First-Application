package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.Utilities.DataManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.init(this);
        if (DataManager.getInstance().leaderBoard != null){
            DataManager.getInstance().leaderBoard = DataManager.getInstance().readArrayScoresFromSP();
        }
    }
}
