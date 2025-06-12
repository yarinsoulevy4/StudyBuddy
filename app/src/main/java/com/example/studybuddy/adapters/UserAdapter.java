package com.example.studybuddy.adapters;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.model.User;
import com.example.studybuddy.screens.AdminPage;
import com.example.studybuddy.screens.Profile;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  implements Filterable {

    /// list of users
    /// @see com.example.studybuddy.model.User
    private final List<User> userList;



    private List<User> userListFilter=new ArrayList<>();

    Context context;

    public UserAdapter(List<User> userList, Context     context) {
        this.userList = userList;

        this.userListFilter.addAll( userList);
        this.context=context;
    }

    /// create a view holder for the adapter
    /// @param parent the parent view group
    /// @param viewType the type of the view
    /// @return the view holder
    /// @see ViewHolder
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /// inflate the item_selected_user layout
        /// @see R.layout.item_selected_user
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_one_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }


    /// bind the view holder with the data
    /// @param holder the view holder
    /// @param position the position of the item in the list
    /// @see ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null) return;

        holder.tvFname.setText(user.getFname());
        holder.tvLname.setText(user.getLname());
        holder.tvPhone.setText(user.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go = new Intent(context, Profile.class);
                go.putExtra("userId", user.getId());
                context.startActivity(go);


            }
        });

    }

    /// get the number of items in the list
    /// @return the number of items in the list
    @Override
    public int getItemCount() {
        return userList.size();
    }


    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(userList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (User user : userList) {
                    if (user.getFullName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userList.clear();
            userList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    /// View holder for the users adapter
    /// @see RecyclerView.ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvFname;
        public final TextView tvLname;
        public final TextView tvPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFname = itemView.findViewById(R.id.tvFname);
            tvLname = itemView.findViewById(R.id.tvLname);
            tvPhone = itemView.findViewById(R.id.tvPhone);


        }
    }
}