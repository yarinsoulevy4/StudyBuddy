package com.example.studybuddy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Lesson;
import com.example.studybuddy.model.Teacher;
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

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvSubject = convertView.findViewById(R.id.tvSubject);
        TextView tvHour = convertView.findViewById(R.id.tvHour);
        TextView tvStudent = convertView.findViewById(R.id.tvStudent);

        Lesson temp = objects.get(position);
        Log.d("LessonAdapter", "Lesson: " + temp.toString());

        tvHour.setText(temp.getHour());
        tvDate.setText(temp.getDate());
        tvSubject.setText(temp.getSubject());

        DatabaseService.getInstance().getTeacher(temp.getId(), new DatabaseService.DatabaseCallback<Teacher>() {
            @Override
            public void onCompleted(Teacher object) {
                    tvStudent.setText(object.getFname());

            }

            @Override
            public void onFailed(Exception e) {
                Log.e("LessonAdapter", "Failed to fetch teacher", e);
            }
        });

        return convertView;
    }
}
