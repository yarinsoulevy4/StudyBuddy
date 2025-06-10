package com.example.studybuddy.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.adapters.TeacherAdapter;
import com.example.studybuddy.adapters.UserAdapter;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {
    RecyclerView rvUser;
    private final DatabaseService databaseService = DatabaseService.getInstance();
    private UserAdapter userAdapter;

    private RecyclerView rcStudent;

    private ArrayList<User> userList=new ArrayList<>();
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        // Initialize Views
      userAdapter=new UserAdapter(userList);
        // Setup RecyclerView

        rcStudent.setAdapter(userAdapter);






        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> users) {

                userList.clear();
                userList.addAll(users);
                userAdapter.notifyDataSetChanged();

                }



            @Override
            public void onFailed(Exception e) {
                Log.e("UserList", e.getMessage());
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (userAdapter != null) {
                    userAdapter.getFilter().filter(newText);

                }
                return true;
            }
        });
    }

    private void initViews() {
        rcStudent = findViewById(R.id.rvUsers_admin);
        searchView=findViewById(R.id.searchView_admin);
        rcStudent.setLayoutManager(new LinearLayoutManager(this));
    }
    }




