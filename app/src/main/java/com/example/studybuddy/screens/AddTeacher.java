package com.example.studybuddy.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;

public class AddTeacher extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "newTeacher";
    Button btnCreateTeacher, btnAddSpinner;  // Added a new button for adding spinners

    Spinner spTeacherSubject;

    String stPrice;
    double price;
    protected String subject="";

    private EditText etSubjects, etPrice;
   public  User user=new User();
    private DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_teacher);  // Make sure the XML layout file is correct
        // user= SharedPreferencesUtil.getUser(AddTeacher.this);
        authenticationService = AuthenticationService.getInstance();
        uid=authenticationService.getCurrentUserId();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();
        initViews();

        Toast.makeText(AddTeacher.this, "Teacher "+ user.toString(), Toast.LENGTH_LONG).show();

        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User u) {
                user = u;
                Toast.makeText(AddTeacher.this, "Teacher " + user.toString(), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void initViews() {
        btnCreateTeacher = findViewById(R.id.btnCreateT);

       btnCreateTeacher.setOnClickListener(this);

        spTeacherSubject = findViewById(R.id.spTsubject);

       spTeacherSubject.setOnItemSelectedListener(this);
        etSubjects=findViewById(R.id.etSubjects);
        etPrice=findViewById(R.id.etTPrice);

         // Initialize the spinner container
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreateTeacher) {
                registerTecher();

        }
        }

        public  void registerTecher(){

            subject=etSubjects.getText().toString();
            stPrice=etPrice.getText().toString();
            price=Double.parseDouble(stPrice);

            // Handle multiple subjects
            // Create a new Teacher object with the updated constructor

            if (user != null) {

                Toast.makeText(AddTeacher.this, "Teacher  222 "+ user.toString(), Toast.LENGTH_SHORT).show();
                Teacher newTeacher = new Teacher(user, subject, price, 0.0,0.0,0);

                // Save the new teacher to the database


                databaseService.createNewTeacher(newTeacher, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        Log.d(TAG, "Teacher added successfully");
                        // Show a success message
                        Toast.makeText(AddTeacher.this, "Teacher Added Successfully", Toast.LENGTH_SHORT).show();

                        /// clear the input fields after adding the food for the next food
                        Log.d(TAG, "Clearing input fields");
                        Intent go = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(go);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e(TAG, "Failed to add food", e);
                        Toast.makeText(AddTeacher.this, "Failed to add food", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }

    // Method to add a new Spinner dynamically
    private void addNewSpinner() {
        // Create a new Spinner instance
        Spinner newSpinner = new Spinner(this);

        // Create an ArrayAdapter for the new spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subjectArr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the new spinner
        newSpinner.setAdapter(adapter);

        // Create layout parameters for the new Spinner
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 20, 0, 0);  // Optional: add margin between spinners

        // Set the layout parameters for the new spinner
        newSpinner.setLayoutParams(params);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position>0) {
            subject += ", " + parent.getItemAtPosition(position);
            etSubjects.setText(subject);
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

