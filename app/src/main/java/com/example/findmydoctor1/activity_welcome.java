package com.example.findmydoctor1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_welcome extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Apply fade-in animation to logo
        ImageView logo = findViewById(R.id.logo);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        logo.startAnimation(fadeIn);

        // Move to Main Activity after SPLASH_TIME
        new Handler().postDelayed(() -> {
            startActivity(new Intent(activity_welcome.this,MainActivity2.class));
            finish(); // Close this activity
        }, SPLASH_TIME);
    }
}
