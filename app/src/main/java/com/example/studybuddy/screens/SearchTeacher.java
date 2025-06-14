package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.adapters.TeacherAdapter;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SearchView;

public class SearchTeacher extends AppCompatActivity {

    private static final String TAG = "SearchTeacher";

    private RecyclerView rcTeacher;
    private TeacherAdapter teacherAdapter;
    private List<Teacher> teacherList;
    private DatabaseService databaseService;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_teacher);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Database Service
        databaseService = DatabaseService.getInstance();

        // Initialize Views
        rcTeacher = findViewById(R.id.rcTeachers);
        searchView = findViewById(R.id.searchView);

        // Setup RecyclerView
        teacherList = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(teacherList, new TeacherAdapter.OnTeacherListener() {
            @Override
            public void onClick(Teacher teacher) {
                Intent go = new Intent(SearchTeacher.this, AddLesson.class);

                if(login.isAdmin){

                     go = new Intent(SearchTeacher.this, Profile.class);
                    go.putExtra("userId", teacher.getId());
                }
                go.putExtra("teacher", teacher);
                startActivity(go);
            }
        });
        rcTeacher.setLayoutManager(new LinearLayoutManager(this));
        rcTeacher.setAdapter(teacherAdapter);

        // Fetch teachers from database
        fetchTeachers();

        // Setup SearchView to filter results dynamically
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                teacherAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                teacherAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void fetchTeachers() {
        databaseService.getTeachers(new DatabaseService.DatabaseCallback<List<Teacher>>() {
            @Override
            public void onCompleted(List<Teacher> object) {
                Log.d(TAG, "Teachers loaded: " + object);
                teacherAdapter.setTeacherList(object);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to load teachers", e);
            }
        });
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
