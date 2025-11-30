package com.example.contactdatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> userList;

    public UserAdapter(Context context, ArrayList<HashMap<String, String>> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> user = userList.get(position);


        holder.tvName.setText(user.get("name"));
        holder.tvPhone.setText(user.get("phone"));

        String imageString = user.get("image");
        if (imageString != null && !imageString.isEmpty()) {
            try {

                int resId = Integer.parseInt(imageString);
                holder.ivAvatar.setImageResource(resId);
            } catch (NumberFormatException e) {

                holder.ivAvatar.setImageResource(R.drawable.avartar1);
            }
        } else {
            holder.ivAvatar.setImageResource(R.drawable.avartar1);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserDetailActivity.class);

            intent.putExtra("USER_ID", Integer.parseInt(user.get("id")));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone;
        ImageView ivAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivAvatar = itemView.findViewById(R.id.ivItemAvatar);
        }
    }
}