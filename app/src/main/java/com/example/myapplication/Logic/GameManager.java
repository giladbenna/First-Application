package com.example.myapplication.Logic;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
    public int life = 3;
    public int odometerScore = 0;
    final int MAX_LEN_SCOREBOARD = 10;
    public int[] leaderBoard = new int[MAX_LEN_SCOREBOARD];

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

    public boolean ifCoin(Drawable coin, Drawable X) {
        return coin.getConstantState().equals(X.getConstantState());
    }
    public boolean isNewRecord(int[] array_leader_board,int newRec){// insert into the array if its in the top 10 and return true or false
            if(newRec > array_leader_board[MAX_LEN_SCOREBOARD-1]) {
                array_leader_board[MAX_LEN_SCOREBOARD - 1] = newRec;
            }
            Arrays.sort(array_leader_board);
        return newRec > array_leader_board[MAX_LEN_SCOREBOARD-1];
    }
}
