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
    private Handler handler;
    private final int SPLASH_DURATION = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        appLogo = findViewById(R.id.imageViewSplash);
        // Initialize handler for UI updates and delayed transition
        handler = new Handler(Looper.getMainLooper());

        // Start animation automatically
        startAnimationSequence();

    }
    private void startAnimationSequence() {
        // Create animation thread
        Thread animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Post UI updates to main thread
                handler.post(() -> {
                    // 1. First animate the logo (scale and fade in)
                    Animation logoAnim = AnimationUtils.loadAnimation(splash.this, R.anim.scale_fade);
                    appLogo.startAnimation(logoAnim);

                    logoAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // 2. After logo animation, animate the title
                            Animation titleAnim = AnimationUtils.loadAnimation(splash.this, R.anim.fade_in);

                            // 3. Schedule transition to MainActivity2
                            handler.postDelayed(() -> {
                                Intent intent = new Intent(splash.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Close the splash screen activity
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }, 2000); // Wait 2 more seconds after animations before transitioning
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                });
            }
        });

        // Start the animation thread
        animationThread.start();
    }}