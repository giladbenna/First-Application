package com.example.myapplication.Logic;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Models.ScoreItem;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
    public int life = 3;
    public int odometerScore = 0;

    public boolean checkIfHit(ImageView obs) {
        return obs.getVisibility() == View.VISIBLE;
    }

    public void delHeartFromManager() {
        life--;
    }

    public void addHeartFromManager() {
        if (life < 3)
            life++;
    }

    public boolean endGame() {
        return life == 0;
    }

    public int findWherePlayerIs(ImageView[] player) {
        for (int i = 0; i < 5; i++) {
            if (player[i].getVisibility() == View.VISIBLE) {
                return i;
            }
        }
        return -1;
    }

    public int getOdometerScore() {
        return odometerScore;
    }

    public boolean ifCoin(Drawable coin) {
        return coin.equals(R.drawable.obstacle_coin);
    }



}
