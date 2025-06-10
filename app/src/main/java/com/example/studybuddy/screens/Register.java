package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;
import com.example.studybuddy.utils.SharedPreferencesUtil;

public class Register extends AppCompatActivity implements View.OnClickListener {

    interface Callback {
        void callback();
    }
    EditText etfname, etlname, etphone, etemail, etpassword;
    String fname, lname, phone, email, password;
     Button btnRegister;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private static final String TAG = "RegisterActivity";

    private AuthenticationService authenticationService;
    private DatabaseService databaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();
    }

    private void initViews() {
        etfname= findViewById(R.id.etfname);
        etlname= findViewById(R.id.etlname);
        etphone= findViewById(R.id.etphone);
        etemail= findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpassword);
        btnRegister=findViewById((R.id.btnRegister));

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        fname=etfname.getText().toString();
        lname=etlname.getText().toString();
        phone=etphone.getText().toString();
        email=etemail.getText().toString();
        password=etpassword.getText().toString();

        boolean isValid=true;
        if (fname.length()<2){

            etfname.setError("שם פרטי קצר מדי");
            isValid = false;
        }
        if (lname.length()<2){
            Toast.makeText(Register.this,"שם משפחה קצר מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (phone.length()<9||phone.length()>10){
            Toast.makeText(Register.this,"מספר הטלפון לא תקין", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if (!email.contains("@")){
            Toast.makeText(Register.this,"כתובת האימייל לא תקינה", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(password.length()<6){
            Toast.makeText(Register.this,"הסיסמה קצרה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(password.length()>20){
            Toast.makeText(Register.this,"הסיסמה ארוכה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (isValid) {
            /// Register user
            registerUser(email, password, () -> {



            });
        }
    }
    /// Register the user
    private void registerUser(String email, String password, Callback callback) {
        Log.d(TAG, "registerUser: Registering user...");

        /// call the sign up method of the authentication service
        authenticationService.signUp(email, password, new AuthenticationService.AuthCallback<String>() {

            @Override
            public void onCompleted(String uid) {
                /// create a new user object
                User neuser=new User(uid, fname, lname,phone, email,password);
                /// call the createNewUser method of the database service
                databaseService.createNewUser(neuser, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        Log.d(TAG, "onCompleted: User registered successfully");
                        /// save the user to shared preferences
                        SharedPreferencesUtil.saveUser(Register.this, neuser);
                        Log.d(TAG, "onCompleted: Redirecting to MainActivity");
                        /// Redirect to MainActivity and clear back stack to prevent user from going back to register screen
                        Intent goLog = new Intent(getApplicationContext(), RegTorS.class);
                        startActivity(goLog);
                    }
                    @Override
                    public void onFailed(Exception e) {
                        Log.e(TAG, "onFailed: Failed to register user", e);
                        /// show error message to user
                        Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                        /// sign out the user if failed to register
                        /// this is to prevent the user from being logged in again
                        authenticationService.signOut();
                    }
                });
            }
            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: Failed to register user", e);
                /// show error message to user
                Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        });
    }

}