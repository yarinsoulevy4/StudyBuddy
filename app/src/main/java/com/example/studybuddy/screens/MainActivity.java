package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void goRegister(View view) {
        // Create an Intent to navigate to the Register activity
        Intent go = new Intent(getApplicationContext(), Register.class);
        startActivity(go); // Start the activity
    }
    public void goLogin(View view)
    {
        Intent go = new Intent(getApplicationContext(),login.class);
        startActivity(go);
    }
    public void goAboutUs(View view)
    {
        Intent go= new Intent(getApplicationContext(), AboutUs.class);
        startActivity(go);
    }

}