package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void goSearchTeacher(View view) {
        Intent go = new Intent(getApplicationContext(), SearchTeacher.class);
        startActivity(go);
    }

    public void goSchedule(View view){
        Intent go = new Intent(getApplicationContext(), StudentSchedule.class);
        startActivity(go);
    }
    public void goProfile(View view) {
        Intent go = new Intent(getApplicationContext(), Profile.class);
        startActivity(go);
    }}



