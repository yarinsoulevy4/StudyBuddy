package com.example.studybuddy.screens;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Lesson;

import org.w3c.dom.Text;

public class LessonProfile extends AppCompatActivity {

    private Lesson lesson;
    TextView tv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lesson = (Lesson) getIntent().getSerializableExtra("lesson");

        initViews();
    }

    private void initViews() {
        tv_status = findViewById(R.id.tv_status);

        if(lesson.getStatus())
            tv_status.setText("accepted");
        else
            tv_status.setText("pending");
    }
}