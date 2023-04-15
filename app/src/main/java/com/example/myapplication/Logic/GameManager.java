package com.example.myapplication.Logic;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;

import java.lang.Math;
public class GameManager {
    public int life = 3;
    public boolean checkIfHit(ImageView obs){
        return obs.getVisibility() == View.VISIBLE;
    }

    public void delHeartFromManager(){
        life--;
    }
    public boolean endGame(){
        return life == 0;
    }

    public int findWherePlayerIs(ImageView[] player) {
        for (int i = 0; i < 3; i++) {
            if (player[i].getVisibility() == View.VISIBLE) {
                return i;
            }
        }
        return -1;
    }

}
