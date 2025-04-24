package com.example.studybuddy.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.TeacherHomePage;
import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AddLesson extends AppCompatActivity implements View.OnClickListener {

    Intent takeit;
    Teacher teacher;
    Button btncreate;

    TextView tVStartDate;
    EditText etteachername;
    EditText etstudentname;
    EditText etclassdetails;
    EditText etSubjects;
    Spinner sphours;

    public User user = new User();
    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;
    String details, hour , subject ;

    private String selectedDate="";

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
            etSubjects.setText(teacher.getSubject().toString());
        }
    }

    private void initViews() {
        etstudentname = findViewById(R.id.etcstudentname);
        etteachername = findViewById(R.id.etcteachername);
        btncreate = findViewById(R.id.btncreate);
        etSubjects = findViewById(R.id.etSubjects);
        sphours = findViewById(R.id.sphours);
        etclassdetails = findViewById(R.id.etlessondetails);

        tVStartDate=findViewById(R.id.tVStartDate);
      //  datePicker = findViewById(R.id.datePicker);

        btncreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String id = DatabaseService.getInstance().generateLessonId();
      //  String zero = "", zero2 = "";

       // if ((datePicker.getMonth() + 1) < 10) zero = "0";  else zero="" ;
       // if ((datePicker.getDayOfMonth()) < 10) zero2 = "0"  ;  else zero2="";


       // String selectedDate = zero2 + datePicker.getDayOfMonth()+"/" + zero + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
        hour = sphours.getSelectedItem().toString();
        details = etclassdetails.getText().toString() + "";
        subject = etSubjects.getText().toString() + "";

        if(!selectedDate.isEmpty()) {
            // Create the Lesson object with proper User and Teacher objects
            Lesson lesson = new Lesson(id, user, teacher, selectedDate, hour, details, subject, null);

            submitLesson(lesson);
        }
        else Toast.makeText(this,"עליך לבחור תאריך",Toast.LENGTH_LONG).show();
    }
    // Method to submit the lesson request
    private void submitLesson(Lesson lesson) {
        databaseService.submitAddLessonForTeacher(lesson, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                databaseService.submitAddLessonForStudent(lesson, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        Toast.makeText(AddLesson.this, "Lesson Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                        resetFields();
                        Intent go = new Intent(AddLesson.this, HomePage.class);
                        startActivity(go);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Toast.makeText(AddLesson.this, "Failed to submit lesson for student", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(AddLesson.this, "Failed to submit request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Reset the fields after submission
    private void resetFields() {
        etstudentname.setText("");
        etteachername.setText("");
        etclassdetails.setText("");
        etSubjects.setText("");
        sphours.setSelection(0);  // Reset spinner to first item

    }




    public void datePick(View view) {

        openDatePickerDialog();
    }

    private void openDatePickerDialog() {
        Calendar calNow = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener,
                calNow.get(Calendar.YEAR),
                calNow.get(Calendar.MONTH),
                calNow.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Choose date");
        datePickerDialog.show();
    }


    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.YEAR, year);
            calSet.set(Calendar.MONTH, month);
            calSet.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            String zero = "", zero2 = "";

            if ((view.getMonth() + 1) < 10) zero = "0";  else zero="" ;
            if ((view.getDayOfMonth()) < 10) zero2 = "0"  ;  else zero2="";


             selectedDate = zero2 + view.getDayOfMonth()+"/" + zero + (view.getMonth() + 1) + "/" + view.getYear();

          //  SimpleDateFormat sdfSave = new SimpleDateFormat("yyyyMMdd");
           // String dateSave = sdfSave.format(calSet.getTime());
            tVStartDate.setText((selectedDate));

        }
    };




}

