package com.example.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.screens.MainActivity;

public class splash extends AppCompatActivity {
    private ImageView appLogo;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appLogo = findViewById(R.id.imageViewSplash);

        startAnimationSequence();
    }

    private void startAnimationSequence() {
        // Load animation
        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.scale_fade);
        appLogo.startAnimation(logoAnim);

        // Listen for animation end
        logoAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Optional: You can do something when animation starts
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Delay and then switch to MainActivity
                handler.postDelayed(() -> {
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }, 2000); // Adjust delay as needed
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Not used
            }
        }); }}