package com.example.studybuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddTeacher extends AppCompatActivity implements View.OnClickListener {


    Button btnCreateTeacher;
    EditText etTeacherName, etTeacherPhone;
    Spinner spTeacherSubject;

    FirebaseDatabase database;  // Database reference
    DatabaseReference myRef;    // Reference to the "Teachers" node in Firebase

    protected String teacherName;
    protected String teacherPhone;
    protected String subject;

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_teacher);  // Make sure the XML layout file is correct
        mAuth = FirebaseAuth.getInstance();
        initViews();

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Teachers").push();  // Create a new entry under the "Teachers" node
        userRef = database.getReference("Teacher").child(Objects.requireNonNull(mAuth.getUid()));

        btnCreateTeacher.setOnClickListener(this);

        // Handle window insets (optional for adjusting the UI with system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("WrongViewCast")
    private void initViews() {
        btnCreateTeacher = findViewById(R.id.btnCreateT);
        etTeacherName = findViewById(R.id.etTname);
        etTeacherPhone = findViewById(R.id.etTphone);
        spTeacherSubject = findViewById(R.id.spTsubject);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreateTeacher) {
            // Retrieve input values from the form
            teacherName = etTeacherName.getText().toString();
            teacherPhone = etTeacherPhone.getText().toString();
            subject = spTeacherSubject.getSelectedItem().toString();

            // Validate inputs
            if (teacherName.isEmpty() || teacherPhone.isEmpty()) {
                // Show an error if required fields are missing
                Toast.makeText(AddTeacher.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new Teacher object with the updated constructor
                Teacher newTeacher = new Teacher(myRef.getKey(), teacherName, teacherPhone, subject);

                // Save the new teacher to the database
                myRef.setValue(newTeacher);

                // Optionally save the teacher reference under the logged-in user
                userRef.child("myTeachers").child(myRef.getKey()).setValue(newTeacher);

                // Show a success message
                Toast.makeText(AddTeacher.this, "Teacher Added Successfully", Toast.LENGTH_SHORT).show();

                // Optionally, clear the form fields
                etTeacherName.setText("");
                etTeacherPhone.setText("");
                spTeacherSubject.setSelection(0);  // Reset spinner to first item

                finish();  // Close the activity
            }
        }
    }
}