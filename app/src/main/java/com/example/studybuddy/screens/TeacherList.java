package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

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

import java.util.List;

public class TeacherList extends AppCompatActivity {
    RecyclerView rvTeacher;
    private DatabaseService databaseService = DatabaseService.getInstance();
    private TeacherAdapter teacherAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    // Initialize RecyclerView
    initViews();

    // Fetch coaches from the database
    databaseService.getTeachers(new DatabaseService.DatabaseCallback<List<Teacher>>() {
        @Override
        public void onCompleted(List<Teacher> coaches) {
            // Pass isAdmin to the adapter
            teacherAdapter = new TeacherAdapter(teachers, new TeacherAdapter().OnTeacherListener() {
                @Override
                public void onTeacherClick(Teacher teacher) {
                    Intent intent = new Intent(TeacherList.this, TeacherProfile.class);
                    intent.putExtra("teacherId", teacher.getId());
                    startActivity(intent);
                }
            });;



           rvTeacher.setAdapter(teacherAdapter);
        }

        @Override
        public void onFailed(Exception e) {
            Log.e("teacherlist", e.getMessage());
        }
    });

    // Set up the search functionality
    SearchView searchView = findViewById(R.id.searchView_admin);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (teacherAdapter != null) {
                teacherAdapter.getFilter().filter(newText);
            }
            return true;
        }
    });
}


private void initViews() {
    rvTeacher = findViewById(R.id.rvTeachers_admin);
    rvTeacher.setLayoutManager(new LinearLayoutManager(this));
}
}