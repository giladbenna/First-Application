package com.example.myapplication.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.Models.ScoreItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class DataManager {

    private static DataManager INSTANCE = null;
    private static final String DB_FILE = "DB_FILE";
    private SharedPreferences sharedPreferences;
    private final int MAX_LEN_SCOREBOARD = 10;
    public ArrayList<ScoreItem> leaderBoard = new ArrayList<>();


    private DataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataManager(context);
        }
    }

    public static DataManager getInstance() {
        return INSTANCE;
    }

    public String getString(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public int getInt(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }


//    private int lastScoreIndex() {// insert into the array if its in the top 10 and return true or false
//        for (int i = 0; i < MAX_LEN_SCOREBOARD; i++) {
//            if (leaderBoard[i] == null) {
//                return i;
//            }
//        }
//        return -1;
//    }

    public void writeScoreToLeaderBoardSP(ScoreItem newRec) {
        leaderBoard.add(newRec);

        Collections.sort(leaderBoard, new ScoreItemComparator());
//      String delete = new Gson().toJson(new ArrayList<>());
        String scores_json = new Gson().toJson(leaderBoard);
        putString("array", scores_json);
    }


    public ArrayList<ScoreItem> readArrayScoresFromSP() {
        String score_json = getString("array", "");
        Type listType = new TypeToken<ArrayList<ScoreItem>>(){}.getType();
        return new Gson().fromJson(score_json, listType);
    }

    public static class ScoreItemComparator implements Comparator<ScoreItem> {
        @Override
        public int compare(ScoreItem item1, ScoreItem item2) {
            // Compare based on the desired parameter
            return item2.getScore() - item1.getScore();
        }

    }
}