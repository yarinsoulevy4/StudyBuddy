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
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class teacherSchedule extends AppCompatActivity {

    ListView lvScheduleTeacher;
    ArrayList<Lesson> lessons = new ArrayList<>();
    LessonAdapter adpSearch1;

    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    String uid;
    Teacher teacher = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();
        uid = authenticationService.getCurrentUserId();

        lvScheduleTeacher = findViewById(R.id.lvScheduleTeacher);
        adpSearch1 = new LessonAdapter(this, lessons);  // Updated adapter initialization
        lvScheduleTeacher.setAdapter(adpSearch1);

        databaseService.getLessonForTeacher(uid,  new DatabaseService.DatabaseCallback<List<Lesson>>() {
            @Override
            public void onCompleted(List<Lesson> object) {
                Log.d("GetCoachSchedule", "Retrieved workouts: " + object.size());
                lessons.addAll(object);
                adpSearch1.notifyDataSetChanged();

                Log.e("lessonsList", lessons.toString());
            }
            @Override
            public void onFailed(Exception e) {
                Log.e("GetTeacherScheduleError", "Error fetching lessons", e);
                // Show error message to the user

                Toast.makeText(teacherSchedule.this, "Failed to fetch lessons", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

