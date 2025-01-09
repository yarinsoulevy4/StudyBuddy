package com.example.studybuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

class AddLesson extends AppCompatActivity implements View.OnClickListener {
    Button btncreate;
    EditText etcteachername,etcstudentname,etlessondetails;
    Spinner spdays,sphours,spsubject ;

    FirebaseDatabase database;//המידע שבענן
    DatabaseReference myRef;


    protected String days;
    protected String hours;
    protected String teachername;
    protected String studentname;
    protected String details;
    protected String subject;


    private DatabaseReference userRef;
    private User user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lesson);
        mAuth = FirebaseAuth.getInstance();
        initViews();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Lessons").push();//מאמת בקשת רשת ומעביר לדפדפן המתאים
        userRef = database.getReference("Teacher").child(Objects.requireNonNull(mAuth.getUid()));

        btncreate.setOnClickListener(this);
    


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @SuppressLint("WrongViewCast")
    private void initViews() {//מוצא לפי הid את הכפתורים, האדיט טקסט וכו
        btncreate = findViewById(R.id.btncreate);
        spdays = findViewById(R.id.spdays);
        sphours = findViewById(R.id.sphours);
        spsubject=(findViewById(R.id.spsubject));
        teachername= String.valueOf(findViewById(R.id.etcteachername));
        studentname= String.valueOf(findViewById(R.id.etcstudentname));
        details= String.valueOf(findViewById(R.id.etlessondetails));


    }

    @Override
    public void onClick(View v) {

        if (v == btncreate) {// הופך את האדיט טקסט לסטרינגים
            days = spdays.getSelectedItem().toString();
            hours = sphours.getSelectedItem().toString();
            subject = spsubject.getSelectedItem().toString();
            details = etlessondetails.getText().toString();


// יוצר שיעור חדש לפי הערכים שהוכנסו
            Lesson newlesson = new Lesson(myRef.getKey(),  teachername, studentname, days, hours, subject, details, "open", login.theUser);

            myRef.setValue(newlesson);

            Lesson newlesson2 = new Lesson(myRef.getKey(), teachername, studentname, days, hours, subject, details, "open", login.theUser);
            userRef = database.getReference("Teacher").child(mAuth.getUid()).child("myLessons");

            userRef.child(myRef.getKey()).setValue(newlesson2);
            finish();

        }

    }
}