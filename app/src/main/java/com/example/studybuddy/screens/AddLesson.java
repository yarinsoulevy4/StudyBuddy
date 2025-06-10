package com.example.studybuddy.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class AddLesson extends AppCompatActivity implements View.OnClickListener {
    Teacher teacher;
    Button btncreate;
    DatePicker dpStartDate;
    TextView tVStartDate;

    EditText etteachername, etstudentname, etclassdetails, etSubjects;
    Spinner sphours;

    public User user = new User();
    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;

    private String selectedDate="";

    String details, hour, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lesson);

        authenticationService = AuthenticationService.getInstance();
        databaseService = DatabaseService.getInstance();

        uid = authenticationService.getCurrentUserId();
        teacher = (Teacher) getIntent().getSerializableExtra("teacher");

        initViews();

        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User student) {
                user = student;
                Toast.makeText(AddLesson.this, "Student " + user.toString(), Toast.LENGTH_SHORT).show();
                etstudentname.setText(user.getFullName());
            }

            @Override
            public void onFailed(Exception e) {
            }
        });

        etteachername.setText(teacher.getFullName());
        etSubjects.setText(teacher.getSubject());
    }


    private void initViews() {
        etstudentname = findViewById(R.id.etcstudentname);
        etteachername = findViewById(R.id.etcteachername);
        btncreate = findViewById(R.id.btncreate);
        etSubjects = findViewById(R.id.etSubjects);
        sphours = findViewById(R.id.sphours);
        etclassdetails = findViewById(R.id.etlessondetails);

        tVStartDate=findViewById(R.id.tVStartDate);

        btncreate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String id = DatabaseService.getInstance().generateLessonId();
        String zero = "", zero2 = "";

        /*if ((dpStartDate.getMonth() + 1) < 10) zero = "0";
        else zero = "";
        if ((dpStartDate.getDayOfMonth()) < 10) zero2 = "0";
        else zero2 = "";

        String selectedDate = zero2 + dpStartDate.getDayOfMonth() + "/" + zero + (dpStartDate.getMonth() + 1) + "/" + dpStartDate.getYear();*/
        hour = sphours.getSelectedItem().toString();
        details = etclassdetails.getText().toString() + "";
        subject = etSubjects.getText().toString() + "";



            final Lesson lesson = new Lesson(id, user.getId(), teacher.getId(), subject, hour, details, selectedDate, null);

        submitLesson(lesson);

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

    // Method to submit the lesson request
    private void submitLesson(Lesson lesson) {
        databaseService.CreateLesson(lesson, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                databaseService.CreateLesson(lesson, new DatabaseService.DatabaseCallback<Void>() {
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

