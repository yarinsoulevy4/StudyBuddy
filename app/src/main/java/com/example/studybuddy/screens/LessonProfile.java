package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LessonProfile extends AppCompatActivity {

    private Lesson lesson;
    TextView tv_status;
    TextView teacherName,StudentName,Hour,Subject,Price,Details;


    DatabaseService databaseService;

    Teacher teacher=null;
    User student=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        databaseService=DatabaseService.getInstance();
        lesson = (Lesson) getIntent().getSerializableExtra("lesson");



       if(lesson!=null) {

           getStudentData();
           getTeacher();
           setData();

       }

    }

    private void getStudentData() {
        databaseService.getUser(lesson.getStudentId(), new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User object) {
                student=object;
                if(student!=null){

                    StudentName.setText(student.getFullName());
                }


            }

            @Override
            public void onFailed(Exception e) {

            }
        });



    }

    private void getTeacher() {

        databaseService.getTeacher(lesson.getTeacherId(), new DatabaseService.DatabaseCallback<Teacher>() {
            @Override
            public void onCompleted(Teacher object) {
                teacher=object;

                if(teacher!=null) {
                    teacherName.setText(teacher.getFullName());
                    Price.setText(teacher.getPrice()+"");
                }


            }

            @Override
            public void onFailed(Exception e) {

            }
        });


    }

    private void setData() {
        if(lesson!=null){





            Hour.setText(lesson.getHour());
            Subject.setText(lesson.getSubject());
            Details.setText(lesson.getDetails());

            if(lesson.getStatus()!=null) {
                if (lesson.getStatus())
                    tv_status.setText("accepted");
                else
                    tv_status.setText("pending");
            }

        }
    }
    private void initViews() {
        tv_status = findViewById(R.id.value_status);
        teacherName= findViewById(R.id.teacherName);
        StudentName = findViewById(R.id.StudentName);
        Hour= findViewById(R.id.Hour);
        Subject= findViewById(R.id.Subject);
        Details= findViewById(R.id.Details);
        Price= findViewById(R.id.Price);



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