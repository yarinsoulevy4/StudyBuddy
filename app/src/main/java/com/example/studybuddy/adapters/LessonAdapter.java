package com.example.studybuddy.adapters;

import android.app.Activity;
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
import com.example.studybuddy.model.User;

import java.util.List;

public class LessonAdapter extends ArrayAdapter<Lesson> {
    private List<Lesson> lessons;
    private LayoutInflater layoutInflater;
    private OnItemLesson onItemLesson;

    private List<User> students;

    public interface OnItemLesson {
        public boolean isShowAccept();
        public boolean isShowReject();
        public void onAccept(Lesson lesson);
        public void onReject(Lesson lesson);
        public void onDetails(Lesson lesson);
    }

    public LessonAdapter(Context context, List<Lesson> lessons, OnItemLesson onItemLesson) {
        super(context, 0, lessons);
        this.onItemLesson = onItemLesson;
        this.lessons = lessons;
        layoutInflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View Holder to reuse views efficiently
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.one_lesson, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder); // Save holder to the convertView
        } else {
            holder = (ViewHolder) convertView.getTag(); // Reuse holder
        }

        if (this.students.isEmpty()) return convertView;

        // Get the Lesson object for this position
        Lesson lesson = lessons.get(position);
        Log.d("LessonAdapter", "Lesson: " + lesson.toString());

        // Set the date and hour for the lesson
        holder.tvDate.setText(lesson.getDate());
        holder.tvHour.setText(lesson.getHour());
        holder.tvSubject.setText(lesson.getSubject());

        User student = null;
        for (User s : this.students) {
            if (s.getId() == lesson.getStudentId()) {
                student = s;
                break;
            }
        }

        if (student != null) {
            holder.tvStudent.setText(student.getFullName());
        }

        holder.btnAccept.setVisibility(onItemLesson.isShowAccept() ? View.VISIBLE : View.GONE);
        holder.btnReject.setVisibility(onItemLesson.isShowReject() ? View.VISIBLE : View.GONE);

        holder.btnAccept.setOnClickListener(v -> onItemLesson.onAccept(lesson));
        holder.btnReject.setOnClickListener(v -> onItemLesson.onReject(lesson));
        convertView.setOnClickListener(v -> onItemLesson.onDetails(lesson));

        return convertView;
    }

    @Override
    public int getCount() {
        if (this.students.isEmpty()) return 0;
        return this.lessons.size();
    }

    public void setLessonList(List<Lesson> lessons) {
        this.lessons.clear();
        this.lessons.addAll(lessons);
        this.notifyDataSetChanged();
    }

    public void setStudentList(List<User> students) {
        this.students = students;
        this.notifyDataSetChanged();
    }


    // ViewHolder pattern for better performance
    static class ViewHolder {
        TextView tvDate, tvSubject, tvHour, tvStudent;
        Button btnAccept, btnReject;


        public ViewHolder(View convertView) {
            tvDate = convertView.findViewById(R.id.tvDateAdapter);
            tvSubject = convertView.findViewById(R.id.tvSubjectAdapter);
            tvHour = convertView.findViewById(R.id.tvHourAdapter);
            tvStudent = convertView.findViewById(R.id.tvStudentNameAdapter);

            btnAccept = convertView.findViewById(R.id.btAccept);
            btnReject = convertView.findViewById(R.id.btDecline);
        }
    }
}
