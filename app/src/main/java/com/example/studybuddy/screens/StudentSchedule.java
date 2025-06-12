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
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class StudentSchedule extends AppCompatActivity {

    ListView lvScheduleStudent;
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


//        getDataFromDB();
    }

//    private void getDataFromDB() {
//        databaseService.getLessonForTeacher(uid, new DatabaseService.DatabaseCallback<List<Lesson>>() {
//            @Override
//            public void onCompleted(List<Lesson> lessonList) {
//
//                if (lessonList.isEmpty())
//                    Log.d("joe", "hiu");
//                adpSearch1.setLessonList(lessonList)
//
//                adpSearch1 = new LessonAdapter(StudentSchedule.this, lessonList, new LessonAdapter.OnItemLesson() {
//                    @Override
//                    public boolean isShowAccept() {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean isShowReject() {
//                        return true;
//                    }
//
//                    @Override
//                    public void onAccept(Lesson lesson) {
//
//                    }
//
//                    @Override
//                    public void onReject(Lesson lesson) {
//
//                    }
//
//                    @Override
//                    public void onDetails(Lesson lesson) {
//                        Intent intent = new Intent(StudentSchedule.this, LessonProfile.class);
//                        intent.putExtra("lesson", lesson);
//                        startActivity(intent);
//                    }
//                });  // Updated adapter initialization
//
//                lvScheduleStudent.setAdapter(adpSearch1);
//
//                databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
//                    @Override
//                    public void onCompleted(List<User> users) {
//                        adpSearch1.setStudentList(users);
//                    }
//
//                    @Override
//                    public void onFailed(Exception e) {
//
//                    }
//                });
//            }
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
//
//
//        }
//
//    }
}