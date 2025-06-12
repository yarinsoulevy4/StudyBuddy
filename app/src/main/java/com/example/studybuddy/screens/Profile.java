package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.firebase.auth.FirebaseAuth;


public class Profile extends AppCompatActivity {
    private EditText etEditUserName, etEditUserEmail, etEditUserPhone;
    private Button btnSaveUser;
    private DatabaseService databaseService;
    private String userId="";
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // אתחול רכיבי UI
        etEditUserName = findViewById(R.id.etEditUserName);
        etEditUserEmail = findViewById(R.id.etEditUserEmail);
        etEditUserPhone = findViewById(R.id.etEditUserPhone);
        btnSaveUser = findViewById(R.id.btnSaveUser);

        // אתחול שירות מסד הנתונים
        databaseService = DatabaseService.getInstance();

        // קבלת המידע של המשתמש הנוכחי (המידע יעבור כ-Intent)
        userId = getIntent().getStringExtra("userId");

        if(userId==null)
            userId= AuthenticationService.getInstance().getCurrentUserId();
        databaseService.getUser(userId, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User user) {
                currentUser = user;
                setView(user);
            }
            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private void setView(User user) {
        etEditUserName.setText(user.getFullName());
        etEditUserEmail.setText(user.getEmail());
        etEditUserPhone.setText(user.getPhone());

        btnSaveUser.setOnClickListener(v -> {
            String updatedName = etEditUserName.getText().toString().trim();
            String updatedEmail = etEditUserEmail.getText().toString().trim();
            String updatedPhone = etEditUserPhone.getText().toString().trim();

            if (TextUtils.isEmpty(updatedName) || TextUtils.isEmpty(updatedEmail) || TextUtils.isEmpty(updatedPhone)) {
                Toast.makeText(Profile.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            // עדכון המידע במסד הנתונים
            currentUser.setFname(updatedName.split(" ")[0]);
            currentUser.setLname(updatedName.split(" ")[1]);
            currentUser.setEmail(updatedEmail);
            currentUser.setPhone(updatedPhone);

            // קריאה לשירות המסד כדי לשמור את השינויים
            databaseService.updateUser(currentUser, new DatabaseService.DatabaseCallback<Void>() {
                @Override
                public void onCompleted(Void v) {
                    Toast.makeText(Profile.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailed(Exception e) {
                    Toast.makeText(Profile.this, "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            });
        });

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