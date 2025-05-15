package com.example.studybuddy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.screens.teacherSchedule;
import com.example.studybuddy.services.DatabaseService;

import java.util.List;

public class LessonAdapter extends ArrayAdapter<Lesson> {
    Context context;
    List<Lesson> objects;


    public LessonAdapter(Context context, List<Lesson> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.one_lesson, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.tvDateAdapter);
        TextView tvSubject = convertView.findViewById(R.id.tvSubjectAdapter);
        TextView tvHour = convertView.findViewById(R.id.tvHourAdapter);
        TextView tvStudent = convertView.findViewById(R.id.tvStudentNameAdapter);

        Button button1 = null,button2 = null;
        Lesson temp = objects.get(position);
        Log.d("LessonAdapter", "Lesson: " + temp.toString());

        tvHour.setText(temp.getHour());
        tvDate.setText(temp.getDate());
        tvSubject.setText(temp.getSubject());


        if (context instanceof teacherSchedule) {
            Log.d("ContextCheck", "This context is an Activity");

            tvStudent.setText(temp.getStudent().getFname()+"  "+temp.getStudent().getLname());

            button1.findViewById(R.id.btAccept);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    temp.setStatus(true);
                    updateLesson(temp);
                }
            });
            button2.findViewById(R.id.btDecline);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    temp.setStatus(false);
                    updateLesson(temp);
                }
            });
        }


        else  tvStudent.setText(temp.getTeacher().getFname()+"  "+temp.getTeacher().getLname());

        return convertView;
    }

    private void updateLesson(Lesson lesson) {
        DatabaseService.getInstance().createNewLesson(lesson, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {

            }
            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
