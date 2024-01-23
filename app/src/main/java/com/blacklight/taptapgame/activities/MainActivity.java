package com.blacklight.taptapgame.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import androidx.core.graphics.Insets;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blacklight.taptapgame.R;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.fade_top2bottom_animation_1000);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.fade_bottom2top_animation_1000);

        ImageView imageLogo = findViewById(R.id.imageLogo);
        TextView textLogo = findViewById(R.id.textLogo);
        String logoName = "Tap-Tap";
        textLogo.setText(logoName.toLowerCase());
        imageLogo.setAnimation(topAnim);
        textLogo.setAnimation(bottomAnim);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Call next activity

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }, SPLASH_SCREEN);
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

}