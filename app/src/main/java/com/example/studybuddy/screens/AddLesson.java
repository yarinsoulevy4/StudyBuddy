package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddLesson extends AppCompatActivity implements View.OnClickListener {

    Intent takeit;
    Teacher teacher;
    Button btncreate;
    EditText etteachername, etstudentname, etclassdetails, subjects;
    Spinner sphours;
    DatePicker datePicker;
    public User user = new User();

    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;
    String details, hour , date , subject ;

    ArrayList<Lesson> teacherLessons= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lesson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authenticationService = AuthenticationService.getInstance();
        uid = authenticationService.getCurrentUserId();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();
        initViews();

        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User u) {
                user = new User(u);
                Toast.makeText(AddLesson.this, "Teacher " + user.toString(), Toast.LENGTH_SHORT).show();
                etstudentname.setText(user.getFname() + " " + user.getLname());
            }

            @Override
            public void onFailed(Exception e) {
            }
        });

        takeit = getIntent();
        teacher = (Teacher) takeit.getSerializableExtra("teacher");

        if (teacher != null) {
            etteachername.setText(teacher.getFname() + " " + teacher.getLname());


            databaseService.getTecherLessons(teacher, new DatabaseService.DatabaseCallback<List<Lesson>>() {


                @Override
               public void onFailed(Exception e) {

                }

                @Override
              public   void onCompleted(List<Lesson> object) {


                    teacherLessons.clear();
                    teacherLessons.addAll(object);
                    // Sort the list by date
                    teacherLessons.sort((l1, l2) -> l2.getDate().compareTo(l1.getDate()));

                }
            });

        }
    }

    private void initViews() {
        etstudentname = findViewById(R.id.etcstudentname);
        etteachername = findViewById(R.id.etcteachername);
        btncreate = findViewById(R.id.btncreate);
        subjects = findViewById(R.id.etSubjects);
        sphours = findViewById(R.id.sphours);
        etclassdetails = findViewById(R.id.etlessondetails);
        datePicker = findViewById(R.id.datePicker);

        btncreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String id = DatabaseService.getInstance().generateLessonId();

        Teacher newTeacher = new Teacher(teacher);
        Lesson lesson = new Lesson(id, user, newTeacher, subject, date, hour, details);
        databaseService.createNewLesson(lesson, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }
}
