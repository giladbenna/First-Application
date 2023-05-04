package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Interfaces.StepCallback;
import com.example.myapplication.Logic.GameManager;
import com.example.myapplication.Utilities.StepDetector;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView main_IMG_background;
    private int[] imageObs;
    private ImageView[][] matrix;
    private ShapeableImageView[] life;
    private final int MAX_ROW = 5;
    private final int MAX_COLUMN = 4;
    private ImageView[] player_row;
    private int DELAY_GEN_OBS = 4000;
    private int DELAY_UPDATE_OBS_ON_MATRIX = 1000;
    private final int DELAY_ODOMETER = 100;
    private final Handler handler_gen_obs = new Handler();
    private final Handler handler_update_on_matrix = new Handler();
    private final Handler handler_odometer = new Handler();
    private final GameManager gameManager = new GameManager();
    private Runnable runnable_upd_mat;
    private Runnable runnable_gen_obs;
    private Runnable runnable_odometer;

    int playerIndex = 0;
    boolean ifHit = false;
    boolean ifEndGame = false;
    boolean ifArrows = false;
    Drawable Obstacle_Coin;
    TextView odometerScore;

    private StepDetector stepDetector;

    private MediaPlayer crashSound;


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        boolean ifArrows = intent.getBooleanExtra("key", false); // or true
        boolean isSlow = intent.getBooleanExtra("slow", false); // or true

        if (!ifArrows) {
            sensorLogic();
            findViewById(R.id.leftArrow).setVisibility(View.INVISIBLE);
            findViewById(R.id.rightArrow).setVisibility(View.INVISIBLE);
        } else {
            if(!isSlow){
                DELAY_GEN_OBS = 2000;
                DELAY_UPDATE_OBS_ON_MATRIX = 800;
            }
            arrowButtonLogic();
        }
        Obstacle_Coin = getResources().getDrawable(R.drawable.obstacle_coin);
        findViews();
//        obstacleCoinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_coin);
//        obstacleCoinResId = R.drawable.obstacle_coin;
        // generate new obs
        generatingObstacles();
        moveObstacle();
        calcOdometer();
        crashSound = MediaPlayer.create(this, R.raw.opening_sound);
        crashSound.start();
        Glide.with(this).load(R.drawable.pokemon_background_forest).centerCrop().into(main_IMG_background);
    }

    private void generatingObstacles() {
        runnable_gen_obs = new Runnable() {
            @Override
            public void run() {
                handler_gen_obs.postDelayed(this, DELAY_GEN_OBS); //Do it again in a second
                int rand = (int) (Math.random() * MAX_ROW);
                int rand2 = (int) (Math.random() * 4);
                matrix[0][rand].setVisibility(View.VISIBLE);
                matrix[0][rand].setImageResource(imageObs[rand2]);
            }
        };
        handler_gen_obs.postDelayed(runnable_gen_obs, 100); //Do it again in a second
    }

    private void moveObstacle() {

        runnable_upd_mat = new Runnable() {
            @Override
            public void run() {
                Drawable Obstacle_Near_Player;
                handler_update_on_matrix.postDelayed(this, DELAY_UPDATE_OBS_ON_MATRIX); //Do it again in a second
                playerIndex = gameManager.findWherePlayerIs(player_row);
                ifHit = gameManager.checkIfHit(matrix[MAX_ROW][playerIndex]);
                for (int column = 0; column <= MAX_COLUMN; column++) {
                    for (int row = MAX_ROW; row >= 0; row--) {
                        if (matrix[row][column].getVisibility() == View.VISIBLE) {
                            matrix[row][column].setVisibility(ImageView.INVISIBLE);
                            if (row != MAX_ROW) {
                                matrix[row + 1][column].setVisibility(ImageView.VISIBLE);
                                Drawable drawable1 = matrix[row][column].getDrawable();
                                matrix[row + 1][column].setImageDrawable(drawable1);
                            }
                        }
                    }
                }

                ifEndGame = gameManager.endGame();
                if (ifHit) {
                    Obstacle_Near_Player = matrix[MAX_ROW][playerIndex].getDrawable();

                    crashSound = MediaPlayer.create(MainActivity.this, R.raw.crash_sound);
                    crashSound.start();

                    if (gameManager.ifCoin(Obstacle_Coin, Obstacle_Near_Player)) {
                        addHeart(life);
                        gameManager.addHeartFromManager();
                    } else {
                        delHeart(life);
                        gameManager.delHeartFromManager();
                        vibrate();
                        if (gameManager.life > 0) {
                            messageOnHit();
                        }
                    }
                }
                if (ifEndGame) {
                    messageOnEndGame();
                    stopRunnable();
                    Intent intent = new Intent(MainActivity.this, ScoreBoardActivity.class);
                    startActivity(intent);
                }
            }
        };
        handler_update_on_matrix.postDelayed(runnable_upd_mat, DELAY_UPDATE_OBS_ON_MATRIX); //Do it again in a second
    }

    private void calcOdometer() {
        runnable_odometer = new Runnable() {
            @Override
            public void run() {
                handler_odometer.postDelayed(this, DELAY_ODOMETER); //Do it again in a second
                gameManager.odometerScore++;
                odometerScore.setText(String.valueOf(gameManager.getOdometerScore()));
            }
        };
        handler_odometer.postDelayed(runnable_odometer, DELAY_ODOMETER); //Do it again in a second

    }

    @Override
    protected void onPause() {

        super.onPause();
        stopRunnable();
        if(!ifArrows)
            stepDetector.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!ifArrows)
            stepDetector.start();
//        runnable_upd_mat.run();
//        runnable_gen_obs.run();
//        runnable_odometer.run();
    }

    private void stopRunnable() {
        handler_update_on_matrix.removeCallbacks(runnable_upd_mat);
        handler_gen_obs.removeCallbacks(runnable_gen_obs);
        handler_odometer.removeCallbacks((runnable_odometer));

    }

    private void vibrate() {
        // Vibrate for 600 milliseconds
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void delHeart(ShapeableImageView[] hearts) {
        for (int i = 2; i >= 0; i--) {
            if (hearts[i].getVisibility() == View.VISIBLE) {
                hearts[i].setVisibility(ShapeableImageView.INVISIBLE);
                break;
            }
        }
    }

    private void addHeart(ShapeableImageView[] hearts) {
        for (int i = 0; i < 3; i++) {
            if (hearts[i].getVisibility() == View.VISIBLE) {
                hearts[i].setVisibility(ShapeableImageView.INVISIBLE);
                break;
            }
        }
    }

    private void messageOnHit() {
        Toast toast = Toast.makeText(this, "Be Careful !", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void messageOnEndGame() {
        Toast toast = Toast.makeText(this, "YOU LOSE !", Toast.LENGTH_LONG);
        toast.show();
    }

    private void arrowButtonLogic() {
        Button leftButton = findViewById(R.id.leftArrow);
        Button rightButton = findViewById(R.id.rightArrow);

        leftButton.setOnClickListener(v -> {
            playerMovementLogicLeft(player_row);
        });
        rightButton.setOnClickListener(v -> {
            playerMovementLogicRight(player_row);
        });
    }

    private void sensorLogic() {
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepYRight() {
                playerMovementLogicRight(player_row);
            }

            @Override
            public void stepYLeft() {
                playerMovementLogicLeft(player_row);
            }
        });
    }

    private void playerMovementLogicLeft(ImageView[] player_row) {
        if (player_row[0].getVisibility() == View.VISIBLE) {
            player_row[0].setVisibility(View.VISIBLE);
        }
        if (player_row[1].getVisibility() == View.VISIBLE) {
            player_row[1].setVisibility(View.INVISIBLE);
            player_row[0].setVisibility(View.VISIBLE);
        }
        if (player_row[2].getVisibility() == View.VISIBLE) {
            player_row[2].setVisibility(View.INVISIBLE);
            player_row[1].setVisibility(View.VISIBLE);
        }
        if (player_row[3].getVisibility() == View.VISIBLE) {
            player_row[3].setVisibility(View.INVISIBLE);
            player_row[2].setVisibility(View.VISIBLE);
        }
        if (player_row[4].getVisibility() == View.VISIBLE) {
            player_row[4].setVisibility(View.INVISIBLE);
            player_row[3].setVisibility(View.VISIBLE);
        }
    }

    private void playerMovementLogicRight(ImageView[] player_row) {
        if (player_row[4].getVisibility() == View.VISIBLE) {
            player_row[4].setVisibility(View.VISIBLE);
        }
        if (player_row[3].getVisibility() == View.VISIBLE) {
            player_row[3].setVisibility(View.INVISIBLE);
            player_row[4].setVisibility(View.VISIBLE);
        }
        if (player_row[2].getVisibility() == View.VISIBLE) {
            player_row[2].setVisibility(View.INVISIBLE);
            player_row[3].setVisibility(View.VISIBLE);
        }
        if (player_row[1].getVisibility() == View.VISIBLE) {
            player_row[1].setVisibility(View.INVISIBLE);
            player_row[2].setVisibility(View.VISIBLE);
        }
        if (player_row[0].getVisibility() == View.VISIBLE) {
            player_row[0].setVisibility(View.INVISIBLE);
            player_row[1].setVisibility(View.VISIBLE);
        }
    }


    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        ImageView[] row_0 = new ImageView[]{
                findViewById(R.id.image_col_00),
                findViewById(R.id.image_col_01),
                findViewById(R.id.image_col_02),
                findViewById(R.id.image_col_03),
                findViewById(R.id.image_col_04)};
        ImageView[] row_1 = new ImageView[]{
                findViewById(R.id.image_col_10),
                findViewById(R.id.image_col_11),
                findViewById(R.id.image_col_12),
                findViewById(R.id.image_col_13),
                findViewById(R.id.image_col_14)};
        ImageView[] row_2 = new ImageView[]{
                findViewById(R.id.image_col_20),
                findViewById(R.id.image_col_21),
                findViewById(R.id.image_col_22),
                findViewById(R.id.image_col_23),
                findViewById(R.id.image_col_24)};
        ImageView[] row_3 = new ImageView[]{
                findViewById(R.id.image_col_30),
                findViewById(R.id.image_col_31),
                findViewById(R.id.image_col_32),
                findViewById(R.id.image_col_33),
                findViewById(R.id.image_col_34)};
        ImageView[] row_4 = new ImageView[]{
                findViewById(R.id.image_col_40),
                findViewById(R.id.image_col_41),
                findViewById(R.id.image_col_42),
                findViewById(R.id.image_col_43),
                findViewById(R.id.image_col_44)};
        ImageView[] row_5 = new ImageView[]{
                findViewById(R.id.image_col_50),
                findViewById(R.id.image_col_51),
                findViewById(R.id.image_col_52),
                findViewById(R.id.image_col_53),
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
                row_0,
                row_1,
                row_2,
                row_3,
                row_4,
                row_5};
        imageObs = new int[]{
                R.drawable.pikachu,
                R.drawable.balbazor,
                R.drawable.squritel,
                R.drawable.obstacle_coin};

        odometerScore = findViewById(R.id.odometerScore);

    }

}
