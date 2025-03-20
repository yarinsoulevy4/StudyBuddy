package com.example.studybuddy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.model.Teacher;
import com.example.studybuddy.screens.AddLesson;

import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> implements Filterable {

    private final List<Teacher> teacherList;  // Original list
     // Filtered list for search
    private final Context context;

    public TeacherAdapter(List<Teacher> teacherList, Context context) {
        this.teacherList = teacherList;

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.one_teacher, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        if (teacher == null) return;

        holder.tvName.setText(teacher.getFname());
        holder.tvRate.setText(String.valueOf(teacher.getRate()));
        holder.tvPrice.setText(String.valueOf(teacher.getPrice()));
        holder.tvSubject.setText(teacher.getSubject());

        holder.relativeLayout.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "Selected: " + teacher.getFname(), Toast.LENGTH_SHORT).show();
            Intent go = new Intent(view.getContext(), AddLesson.class);
            go.putExtra("teacher", teacher);
            context.startActivity(go);
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchQuery = constraint != null ? constraint.toString().toLowerCase().trim() : "";
                List<Teacher> filteredList = new ArrayList<>();

                if (searchQuery.isEmpty()) {
                    filteredList.addAll(teacherList); // Show all teachers if search is empty
                } else {
                    for (Teacher teacher : teacherList) {
                        // Check if teacher's name, subject, or price contains the search query
                        if ((teacher.getFname() != null && teacher.getFname().toLowerCase().contains(searchQuery)) ||
                                (teacher.getSubject() != null && teacher.getSubject().toLowerCase().contains(searchQuery)) ||
                                (String.valueOf(teacher.getPrice()).contains(searchQuery))) {
                            filteredList.add(teacher);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                teacherList.clear();
                teacherList.addAll((List<Teacher>) results.values);  // Update filtered list
                notifyDataSetChanged();
            }
        };
    }

    // Method to update the data in the adapter
    public void updateData(List<Teacher> newList) {
        teacherList.clear();
        teacherList.addAll(newList);
      //  filteredTeacherList.clear();
      //  filteredTeacherList.addAll(newList);
        notifyDataSetChanged();
    }

    // ViewHolder for the teacher item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName, tvPrice, tvSubject, tvRate;
        public final ConstraintLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvTSubjects);
            tvPrice = itemView.findViewById(R.id.tvTPrice);
            tvRate = itemView.findViewById(R.id.tvTRate);
            tvName = itemView.findViewById(R.id.tvTName);
            relativeLayout = itemView.findViewById(R.id.clT);
        }
    }
}
