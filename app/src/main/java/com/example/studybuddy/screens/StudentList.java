package com.example.studybuddy.screens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.adapters.UserAdapter;
import com.example.studybuddy.model.User;
import com.example.studybuddy.services.DatabaseService;

import java.util.List;

public class StudentList extends AppCompatActivity {
    RecyclerView rvUser;
    private final DatabaseService databaseService = DatabaseService.getInstance();
    private UserAdapter userAdapter;

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

        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User>trainees) {
                traineeAdapter = new TraineeAdapter(trainees, trainee -> {
                    Intent intent = new Intent(TraineeList.this, TraineeProfile.class);
                    intent.putExtra("traineeId", trainee.getId());
                    startActivity(intent);
                });
                rvTrainees.setAdapter(traineeAdapter);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("TraineeList", e.getMessage());
            }
        });
        SearchView searchView = findViewById(R.id.searchView_admin);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (traineeAdapter != null) {
                    traineeAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    private void initViews() {
        rvTrainees = findViewById(R.id.rvTrainees);
        rvTrainees.setLayoutManager(new LinearLayoutManager(this));
    }
    }




