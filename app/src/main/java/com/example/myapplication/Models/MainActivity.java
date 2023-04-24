package com.example.myapplication.Models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.myapplication.Logic.GameManager;
import com.example.myapplication.R;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView main_IMG_background;
    private int[] imageObs;
    private ImageView[][] matrix;
    private ShapeableImageView[] life;
    private final int MAX_COLUMN = 5;
    private ImageView[] player_row;
    private final int DELAY_GEN_OBS = 2000;
    private final int DELAY_UPDATE_OBS_ON_MATRIX = 1000;
    private final Handler handler_gen_obs = new Handler();
    private final Handler handler_update_on_matrix = new Handler();
    private final GameManager gameManager = new GameManager();
    private Runnable runnable_upd_mat;
    private Runnable runnable_gen_obs;
    int playerIndex = 0;
    boolean hit = false;
    boolean endGame = false;
    Drawable Obstacle_Near_Player;
    Drawable Obstacle_Coin;

    public MainActivity() {
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViews();
        Obstacle_Coin = getResources().getDrawable((R.drawable.obstacle_coin));
        // generate new obs
        generatingObstacles();
        moveObstacle();
        Glide.with(this).load(R.drawable.backgroundpokemon).centerCrop().into(main_IMG_background);
    }

    private void generatingObstacles()
    {
        runnable_gen_obs = new Runnable() {
            @Override
            public void run() {
                handler_gen_obs.postDelayed(this, DELAY_GEN_OBS); //Do it again in a second
                int rand = (int) (Math.random() * MAX_COLUMN);
                int rand2 = (int) (Math.random() * 4);
                matrix[rand][0].setVisibility(View.VISIBLE);
                //matrix[rand][0].setImageDrawable(imageObs[rand2]);
                matrix[rand][0].setImageResource(imageObs[rand2]);
            }
        };
        handler_gen_obs.postDelayed(runnable_gen_obs, 100); //Do it again in a second
    }

    private void moveObstacle() {

        runnable_upd_mat = new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {
                handler_update_on_matrix.postDelayed(this, DELAY_UPDATE_OBS_ON_MATRIX); //Do it again in a second
                playerIndex = gameManager.findWherePlayerIs(player_row);
                hit = gameManager.checkIfHit(matrix[playerIndex][MAX_COLUMN]);
                for(int j = 0; j < MAX_COLUMN; j++){
                    for(int i = MAX_COLUMN ; i >= 0 ; i--){
                        if(matrix[j][i].getVisibility() == View.VISIBLE) {
                            matrix[j][i].setVisibility(ImageView.INVISIBLE);
                            if(i!=MAX_COLUMN){
                                matrix[j][i+1].setVisibility(ImageView.VISIBLE);
                                Drawable drawable1 = matrix[j][i].getDrawable();
                                matrix[j][i+1].setImageDrawable(drawable1);
                            }
                        }
                    }
                }

                ButtonLog();

                endGame = gameManager.endGame();
                Obstacle_Near_Player = matrix[playerIndex][MAX_COLUMN].getDrawable();
//                Obstacle_Coin = getResources().getDrawable((R.drawable.obstacle_coin));


                if(hit){
                    if(Obstacle_Near_Player.equals(Obstacle_Coin)){

                    }
                    else {
                        delHeart(life);
                        gameManager.delHeartFromManager();
                        vibrate();
                        if (gameManager.life > 0) {
                            messageOnHit();
                        }
                    }
                }
                if(endGame){
                    messageOnEndGame();
                    stopRunnable();
                }
            }
        };
        handler_update_on_matrix.postDelayed(runnable_upd_mat, DELAY_UPDATE_OBS_ON_MATRIX); //Do it again in a second
    }
    private void stopRunnable() {
        handler_update_on_matrix.removeCallbacks(runnable_upd_mat);
        handler_gen_obs.removeCallbacks(runnable_gen_obs);
    }
    private void vibrate(){
        // Vibrate for 600 milliseconds
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE));
    }
    private void delHeart(ShapeableImageView[] hearts){
        for (int i = 0; i < 3; i++) {
            if (hearts[i].getVisibility() == View.VISIBLE) {
                hearts[i].setVisibility(ShapeableImageView.INVISIBLE);
                break;
            }
        }
    }

    private void messageOnHit(){
        Toast toast = Toast.makeText(this,"Be Careful !",Toast.LENGTH_SHORT);
        toast.show();
    }
    private void messageOnEndGame(){
        Toast toast = Toast.makeText(this,"YOU LOSE !",Toast.LENGTH_LONG);
        toast.show();
    }

    private void ButtonLog(){
        Button leftButton = findViewById(R.id.leftArrow);
        Button rightButton = findViewById(R.id.rightArrow);
        leftButton.setOnClickListener(v -> {
            if(player_row[0].getVisibility() == View.VISIBLE){
                player_row[0].setVisibility(View.VISIBLE);
            }
            if(player_row[1].getVisibility() == View.VISIBLE){
                player_row[1].setVisibility(View.INVISIBLE);
                player_row[0].setVisibility(View.VISIBLE);
            }
            if(player_row[2].getVisibility() == View.VISIBLE){
                player_row[2].setVisibility(View.INVISIBLE);
                player_row[1].setVisibility(View.VISIBLE);
            }
            if(player_row[3].getVisibility() == View.VISIBLE){
                player_row[3].setVisibility(View.INVISIBLE);
                player_row[2].setVisibility(View.VISIBLE);
            }
            if(player_row[4].getVisibility() == View.VISIBLE){
                player_row[4].setVisibility(View.INVISIBLE);
                player_row[3].setVisibility(View.VISIBLE);
            }
        });
        rightButton.setOnClickListener(v -> {
            if(player_row[4].getVisibility() == View.VISIBLE){
                player_row[4].setVisibility(View.VISIBLE);
            }
            if(player_row[3].getVisibility() == View.VISIBLE){
                player_row[3].setVisibility(View.INVISIBLE);
                player_row[4].setVisibility(View.VISIBLE);
            }
            if(player_row[2].getVisibility() == View.VISIBLE){
                player_row[2].setVisibility(View.INVISIBLE);
                player_row[3].setVisibility(View.VISIBLE);
            }
            if(player_row[1].getVisibility() == View.VISIBLE){
                player_row[1].setVisibility(View.INVISIBLE);
                player_row[2].setVisibility(View.VISIBLE);
            }
            if(player_row[0].getVisibility() == View.VISIBLE){
                player_row[0].setVisibility(View.INVISIBLE);
                player_row[1].setVisibility(View.VISIBLE);
            }
        });
    }


    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        ImageView[] col_0 = new ImageView[]{
                findViewById(R.id.image_col_00),
                findViewById(R.id.image_col_10),
                findViewById(R.id.image_col_20),
                findViewById(R.id.image_col_30),
                findViewById(R.id.image_col_40),
                findViewById(R.id.image_col_50)};
        ImageView[] col_1 = new ImageView[]{
                findViewById(R.id.image_col_01),
                findViewById(R.id.image_col_11),
                findViewById(R.id.image_col_21),
                findViewById(R.id.image_col_31),
                findViewById(R.id.image_col_41),
                findViewById(R.id.image_col_51)};
        ImageView[] col_2 = new ImageView[]{
                findViewById(R.id.image_col_02),
                findViewById(R.id.image_col_12),
                findViewById(R.id.image_col_22),
                findViewById(R.id.image_col_32),
                findViewById(R.id.image_col_42),
                findViewById(R.id.image_col_52)};
        ImageView[] col_3 = new ImageView[]{
                findViewById(R.id.image_col_03),
                findViewById(R.id.image_col_13),
                findViewById(R.id.image_col_23),
                findViewById(R.id.image_col_33),
                findViewById(R.id.image_col_43),
                findViewById(R.id.image_col_53)};
        ImageView[] col_4 = new ImageView[]{
                findViewById(R.id.image_col_04),
                findViewById(R.id.image_col_14),
                findViewById(R.id.image_col_24),
                findViewById(R.id.image_col_34),
                findViewById(R.id.image_col_44),
                findViewById(R.id.image_col_54)};
        player_row = new ImageView[]{
                findViewById(R.id.Player_0),
                findViewById(R.id.Player_1),
                findViewById(R.id.Player_2),
                findViewById(R.id.Player_3),
                findViewById(R.id.Player_4)};
        life = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};

        matrix = new ImageView[][]{
                col_0,
                col_1,
                col_2,
                col_3,
                col_4};
        imageObs = new int[]{
                R.drawable.pikachu,
                R.drawable.balbazor,
                R.drawable.squritel,
                R.drawable.obstacle_coin};

        }

    }
