package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.services.AuthenticationService;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {

    Button btnGoStudentList, btnGoTeacherList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });;

    initViews();
    }

    private void initViews() {

        btnGoStudentList=findViewById(R.id.btnGoUserList);
        btnGoTeacherList=findViewById(R.id.btnGoTeacherList);

        btnGoTeacherList.setOnClickListener(this);
        btnGoStudentList.setOnClickListener(this);

    }



    // This method should be placed outside of onCreate() method
    public void goAdd(View view){
        Intent go = new Intent(getApplicationContext(),AddTeacher.class);
        startActivity(go);
    }

    @Override
    public void onClick(View v) {

        if(v==btnGoStudentList){
            goStudentList();


        }
        else
        if(v==btnGoTeacherList){
            goTeacherList();


        }



}
    public void goStudentList() {
        Intent go = new Intent(getApplicationContext(), StudentList.class);
        startActivity(go);
    }
    public void goTeacherList() {
        Intent go = new Intent(getApplicationContext(), SearchTeacher.class);
        startActivity(go);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent go = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(go);
            return true;
        } else if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut(); // Log out the user

            Intent goLogin = new Intent(getApplicationContext(), login.class);
            goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(goLogin);
            finish(); // Finish current activity
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



}

