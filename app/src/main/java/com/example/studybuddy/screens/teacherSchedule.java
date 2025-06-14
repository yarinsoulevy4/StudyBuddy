package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class teacherSchedule extends AppCompatActivity {

    ListView lvScheduleTeacher;
    ArrayList<Lesson> lessons = new ArrayList<>();
    LessonAdapter adpSearch1;

    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    String uid;

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

        adpSearch1 = new LessonAdapter(teacherSchedule.this, new LessonAdapter.OnItemLesson() {
            @Override
            public boolean isShowAccept(Lesson lesson) {
                return lesson.getStatus() == null;
            }

            @Override
            public boolean isShowReject(Lesson lesson) {
                return lesson.getStatus() == null;
            }

            @Override
            public void onAccept(Lesson lesson) {
                lesson.setStatus(true);
                updateLesson(lesson);
            }

            @Override
            public void onReject(Lesson lesson) {
                lesson.setStatus(false);
                updateLesson(lesson);
            }

            @Override
            public void onDetails(Lesson lesson) {
                Intent intent = new Intent(teacherSchedule.this, LessonProfile.class);
                intent.putExtra("lesson", lesson);
                startActivity(intent);
            }
        });  // Updated adapter initialization

        lvScheduleTeacher.setAdapter(adpSearch1);

        getDataFromDB();
    }

    private void getDataFromDB() {
        databaseService.getLessonForTeacher(uid, new DatabaseService.DatabaseCallback<List<Lesson>>() {
            @Override
            public void onCompleted(List<Lesson> lessonList) {
                Log.d("GetCoachSchedule", "Retrieved lessons: " + lessons.size());

                String teacherId = AuthenticationService.getInstance().getCurrentUserId();
                lessonList.removeIf(lesson -> !Objects.equals(lesson.getTeacherId(), teacherId));
                adpSearch1.setLessonList(lessonList);

            }

            @Override
            public void onFailed(Exception e) {
                Log.e("GetTeacherScheduleError", "Error fetching lessons", e);
                // Show error message to the user
                Toast.makeText(teacherSchedule.this, "Failed to fetch lessons", Toast.LENGTH_SHORT).show();
            }
        });

        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> users) {
                adpSearch1.setStudentList(users);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void updateLesson(Lesson lesson) {
        databaseService.CreateLesson(lesson, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Toast.makeText(teacherSchedule.this, "lesson was updated", Toast.LENGTH_SHORT).show();
                if (lesson.getStatus() == false) {
                    lessons.remove(lesson);
                }
                adpSearch1.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {

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

