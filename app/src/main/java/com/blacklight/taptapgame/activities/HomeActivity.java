package com.blacklight.taptapgame.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.blacklight.taptapgame.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView score;
    private ImageView redImage, greenImage, blueImage, yellowImage;
    private int clicked = -1, actual = -1;
    private int scoreCount = 0;
    private int countDown = 3;
    private MediaPlayer mediaPlayer;
    private boolean gameEnded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        score = findViewById(R.id.score_text_view);
        String initMessage = "Be Ready!";
        score.setText(initMessage);

        MaterialCardView red = findViewById(R.id.red_card_view);
        redImage = findViewById(R.id.red_image_view);

        MaterialCardView green = findViewById(R.id.green_card_view);
        greenImage = findViewById(R.id.green_image_view);

        MaterialCardView blue = findViewById(R.id.blue_card_view);
        blueImage = findViewById(R.id.blue_image_view);

        MaterialCardView yellow = findViewById(R.id.yellow_card_view);
        yellowImage = findViewById(R.id.yellow_image_view);

        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);

        // game countdown
        countDown();

        // game started
        new Handler(Looper.getMainLooper()).postDelayed(this::startGame, 4500);
    }

    private void countDown() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (countDown == 0) {
                removeHandler(handler);
                String scoreText = "Score: " + getScore();
                score.setText(scoreText);
            }
            else {
                stopSound();
                playSound(6);
                String countDownText = "" + countDown;
                score.setText(countDownText);
                countDown--;
                countDown();
            }
        }, 1000);
    }

    private void startGame() {
        Handler handler = new Handler(Looper.getMainLooper());
        actual = (int) (Math.random() * 4);
        switch (actual) {
            case 0:
                stopSound();
                playSound(1);
                redImage.setImageResource(R.drawable.gray_box);
                break;
            case 1:
                stopSound();
                playSound(2);
                greenImage.setImageResource(R.drawable.gray_box);
                break;
            case 2:
                stopSound();
                playSound(3);
                blueImage.setImageResource(R.drawable.gray_box);
                break;
            case 3:
                stopSound();
                playSound(4);
                yellowImage.setImageResource(R.drawable.gray_box);
                break;
        }
        handler.postDelayed(() -> {
            if (clicked == -1 || clicked != actual) {
                // stop the game
                stopSound();
                if (!gameEnded) {
                    playSound(5);
                    gameEnded = true;
                }
                removeHandler(handler);
                createDialog();
            }
            else {
                clicked = -1;
                stopSound();
                switch (actual) {
                    case 0:
                        redImage.setImageResource(R.drawable.red_box);
                        break;
                    case 1:
                        greenImage.setImageResource(R.drawable.green_box);
                        break;
                    case 2:
                        blueImage.setImageResource(R.drawable.blue_box);
                        break;
                    case 3:
                        yellowImage.setImageResource(R.drawable.yellow_box);
                        break;
                }
                startGame();
            }
        }, 1200);
    }

    private void removeHandler(Handler handler) {
        handler.removeCallbacksAndMessages(null);
    }

    private void config() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        };
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);

        gameEnded = false;
    }

    //  Methods for scoreCount
    private int getScore() {
        return this.scoreCount;
    }
    private void increaseScore() {
        this.scoreCount++;
    }

    // click listener method
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.green_card_view) {
            if (actual == 1) {
                increaseScore();
            }
            else {
                stopSound();
                playSound(5);
                gameEnded = true;
            }
            clicked = 1;
        }
        else if (v.getId() == R.id.red_card_view) {
            if (actual == 0) {
                increaseScore();
            }
            else {
                stopSound();
                playSound(5);
                gameEnded = true;
            }
            clicked = 0;
        }
        else if (v.getId() == R.id.blue_card_view) {
            if (actual == 2) {
                increaseScore();
            }
            else {
                stopSound();
                playSound(5);
                gameEnded = true;
            }
            clicked = 2;
        }
        else if (v.getId() == R.id.yellow_card_view) {
            if (actual == 3) {
                increaseScore();
            }
            else {
                stopSound();
                playSound(5);
                gameEnded = true;
            }
            clicked = 3;
        }
        String scoreText = "Score: " + getScore();
        score.setText(scoreText);
    }

    private void createDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setCustomTitle(getLayoutInflater().inflate(R.layout.dialog_title, null));
        builder.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dialog_background, null));
        View view = getLayoutInflater().inflate(R.layout.dialog_message, null);
        TextView tempTextView = view.findViewById(R.id.dialog_msg);
        String message = "Your score is " + getScore();
        tempTextView.setText(message);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Play Again", (dialog, which) -> {
            dialog.dismiss();
            recreate();
        });
        builder.setNegativeButton("Exit", (dialog, which) -> {
            dialog.dismiss();
            finishAffinity();
        });
        builder.show();
    }

    private void playSound(int sound) {
        int id = -1;
        switch (sound) {
            case 1:
                id = R.raw.red;
                break;
            case 2:
                id = R.raw.green;
                break;
            case 3:
                id = R.raw.blue;
                break;
            case 4:
                id = R.raw.yellow;
                break;
            case 5:
                id = R.raw.wrong;
                break;
            case 6:
                id = R.raw.countdown;
                break;
            default:
                break;
        }
        if (id == -1) {
            return;
        }
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, id);
        }
        mediaPlayer.start();
    }

    private void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopSound();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopSound();
    }
}
