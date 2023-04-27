package com.example.myapplication.Logic;
import android.view.View;
import android.widget.ImageView;

public class GameManager {
    public int life = 11;
    public boolean checkIfHit(ImageView obs){
        return obs.getVisibility() == View.VISIBLE;
    }

    public void delHeartFromManager(){
        life--;
    }
    public void addHeartFromManager(){
        if(life < 11)
            life++;
    }
    public boolean endGame(){
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

}
