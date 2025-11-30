package com.example.contactdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class UserDetailActivity extends AppCompatActivity {

    TextView tvDetailName, tvDetailEmail, tvDetailPhone;
    ImageView ivDetailAvatar;
    Button btnEditUser, btnDeleteUser;
    DatabaseHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_user);

        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailEmail = findViewById(R.id.tvDetailEmail);
        tvDetailPhone = findViewById(R.id.tvDetailPhone);
        ivDetailAvatar = findViewById(R.id.ivDetailAvatar);

        btnEditUser = findViewById(R.id.btnEditUser);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);

        dbHelper = new DatabaseHelper(this);
        userId = getIntent().getIntExtra("USER_ID", -1);

        loadUserDetail();

        btnEditUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailActivity.this, UpdateUserActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        btnDeleteUser.setOnClickListener(v -> {
            dbHelper.deleteUser(userId);
            Toast.makeText(this, "Delete successful!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    private void loadUserDetail() {
        HashMap<String, String> user = dbHelper.getUserById(userId);
        tvDetailName.setText("Full name: " + user.get("name"));
        tvDetailEmail.setText("Email: " + user.get("email"));
        tvDetailPhone.setText("Phone number: " + user.get("phone"));


        String imageString = user.get("image");
        if (imageString != null && !imageString.isEmpty()) {
            try {
                int resId = Integer.parseInt(imageString);
                ivDetailAvatar.setImageResource(resId);
            } catch (NumberFormatException e) {
                ivDetailAvatar.setImageResource(R.drawable.avartar1);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserDetail();
    }
}