package com.example.studybuddy.screens;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.adapters.LessonAdapter;
import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class StudentSchedule extends AppCompatActivity {

    ListView lvScheduleStudent;
    ArrayList<Lesson> lessons = new ArrayList<>();
    LessonAdapter adpSearch1;

    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();
        uid = authenticationService.getCurrentUserId();

        lvScheduleStudent = findViewById(R.id.lvScheduleStudent);
        adpSearch1 = new LessonAdapter(this, lessons);  // Updated adapter initialization
        lvScheduleStudent.setAdapter(adpSearch1);

        databaseService.getLessonForTeacher(uid, new DatabaseService.DatabaseCallback<List<Lesson>>() {
            @Override
            public void onCompleted(List<Lesson> object) {
                Log.d("GetStudentSchedule", "Retrieved lessons: " + object.size());
                lessons.addAll(object);
                adpSearch1.notifyDataSetChanged();

                Log.e("lessonslist", lessons.toString());
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("GetStudentScheduleError", "Error fetching workouts", e);
                // Show error message to the user

                Toast.makeText(StudentSchedule.this, "Failed to fetch lessons", Toast.LENGTH_SHORT).show();

            }
        });
    }

}