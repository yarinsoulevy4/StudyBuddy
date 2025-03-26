package com.example.studybuddy.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.TeacherHomePage;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.AuthenticationService;
import com.example.studybuddy.services.DatabaseService;
import com.example.studybuddy.utils.SharedPreferencesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "loginToFireBase";

    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;

    String email2, pass2;

    String admin = "levyarin14@gmail.com";
    String adminpass ="010407";




    public static User theUser=null;

    public static Boolean isAdmin=false;
    public  static  Teacher teacher=null;





    private AuthenticationService authenticationService;
    private DatabaseService databaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        databaseService=DatabaseService.getInstance();




        init_views();





    }

    private void init_views() {
        btnLogin = findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(this);
        etEmailLogin = findViewById(R.id.etemaillogin);
        etPasswordLogin = findViewById(R.id.etPasswordlogin);


        theUser=SharedPreferencesUtil.getUser(login.this);

        if(theUser!=null) {
            email2 = theUser.getEmail();
            pass2 = theUser.getPassword();
            etEmailLogin.setText(email2);
            etPasswordLogin.setText(pass2);
        }

    }

    @Override
    public void onClick(View v) {
        email2 = etEmailLogin.getText().toString();
        pass2 = etPasswordLogin.getText().toString();
        //Toast.makeText(login.this,email2+" ",Toast.LENGTH_LONG).show();

        /// Login user
        loginUser(email2, pass2);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void goIn(View view) {
        Intent go = new Intent(getApplicationContext(), HomePage.class);
        startActivity(go);
    }

    private void loginUser(String email, String password) {
        authenticationService.signIn(email, password, new AuthenticationService.AuthCallback<String>() {
            /// Callback method called when the operation is completed
            ///
            /// @param uid the user ID of the user that is logged in
            @Override
            public void onCompleted(String uid) {
                Log.d(TAG, "onCompleted: User logged in successfully");
                /// get the user data from the database

                if (email2.equals(admin) && pass2.equals(adminpass)) {
                    Intent goLog = new Intent(getApplicationContext(), AdminPage.class);
                    isAdmin = true;
                    startActivity(goLog);
                }

                databaseService.getTeacher(uid, new DatabaseService.DatabaseCallback<Teacher>() {
                    @Override
                    public void onCompleted(Teacher obj) {
                        teacher = obj;

                        Log.d(TAG, "onCompleted: User data retrieved successfully");
                        /// save the user data to shared preferences

                        /// Redirect to main activity and clear back stack to prevent user from going back to login screen
                        Intent mainIntent = new Intent(login.this, TeacherHomePage.class);
                        /// Clear the back stack (clear history) and start the MainActivity
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        if (teacher != null) {
                            SharedPreferencesUtil.saveUser(login.this, teacher);
                            startActivity(mainIntent);

                        }
                        else {

                            databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {


                                @Override
                                public void onCompleted(User object) {

                                    theUser = object;

                                    Log.d(TAG, "onCompleted: User data retrieved successfully");
                                    /// save the user data to shared preferences
                                     SharedPreferencesUtil.saveUser(login.this, theUser);
                                    /// Redirect to main activity and clear back stack to prevent user from going back to login screen
                                    Intent mainIntent = new Intent(login.this, HomePage.class);
                                    /// Clear the back stack (clear history) and start the MainActivity
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);


                                }

                                @Override
                                public void onFailed(Exception e) {
                                    Log.e(TAG, "onFailed: Failed to retrieve user data", e);
                                    //getUser

                                }
                            });


                        }

                    }

                    @Override
                    public void onFailed(Exception e) {
                        teacher = null;

                        //getTeacher


                    }
                });

            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: Failed to log in user", e);
                /// Show error message to user
                etEmailLogin.setError("Invalid email or password"+ email2);
                etPasswordLogin.requestFocus();
                /// Sign out the user if failed to retrieve user data
                /// This is to prevent the user from being logged in again
                authenticationService.signOut();

            }
        });
    }

}