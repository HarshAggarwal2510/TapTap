package com.blacklight.taptapgame.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.blacklight.taptapgame.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView score;
    private MaterialCardView red, green, blue, yellow;
    private ImageView redImage, greenImage, blueImage, yellowImage;
    private int clicked = -1, actual = -1;
    private int scoreCount = 0;
    private int countDown = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        score = findViewById(R.id.score_text_view);
        String initMessage = "Be Ready!";
        score.setText(initMessage);

        red = findViewById(R.id.red_card_view);
        redImage = findViewById(R.id.red_image_view);

        green = findViewById(R.id.green_card_view);
        greenImage = findViewById(R.id.green_image_view);

        blue = findViewById(R.id.blue_card_view);
        blueImage = findViewById(R.id.blue_image_view);

        yellow = findViewById(R.id.yellow_card_view);
        yellowImage = findViewById(R.id.yellow_image_view);

        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);

//        handler = new Handler(Looper.getMainLooper());
        MaterialCardView[] cards = {red, green, blue, yellow};

        // game countdown
        countDown();

        // game started
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startGame(cards);
            }
        }, 4500);
    }

    private void countDown() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (countDown == 0) {
                    removeHandler(handler);
                    String scoreText = "Score: " + getScore();
                    score.setText(scoreText);
                }
                else {
                    String countDownText = "" + countDown;
                    score.setText(countDownText);
                    countDown--;
                    countDown();
                }
            }
        }, 1000);
    }

    private void startGame(MaterialCardView[] cards) {
        Handler handler = new Handler(Looper.getMainLooper());
        actual = (int) (Math.random() * 4);
        switch (actual) {
            case 0:
                redImage.setImageResource(R.drawable.gray_box);
                break;
            case 1:
                greenImage.setImageResource(R.drawable.gray_box);
                break;
            case 2:
                blueImage.setImageResource(R.drawable.gray_box);
                break;
            case 3:
                yellowImage.setImageResource(R.drawable.gray_box);
                break;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (clicked == -1 || clicked != actual) {
                    // stop the game
                    removeHandler(handler);
                    createDialog();
                }
                else {
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
                    startGame(cards);
                }
            }
        }, 1500);
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
            clicked = 1;
        }
        else if (v.getId() == R.id.red_card_view) {
            if (actual == 0) {
                increaseScore();
            }
            clicked = 0;
        }
        else if (v.getId() == R.id.blue_card_view) {
            if (actual == 2) {
                increaseScore();
            }
            clicked = 2;
        }
        else if (v.getId() == R.id.yellow_card_view) {
            if (actual == 3) {
                increaseScore();
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
}
