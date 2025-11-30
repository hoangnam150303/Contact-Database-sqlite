package com.example.contactdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateUserActivity extends AppCompatActivity {
    EditText etUpdateName, etUpdateEmail, etUpdatePhone;
    Button btnUpdateUser;
    DatabaseHelper dbHelper;
    int userId;


    LinearLayout layoutAvatarContainer;
    int[] images = {
            R.drawable.avartar1,
            R.drawable.avartar2,
            R.drawable.avartar3,
            R.drawable.avartar4,
            R.drawable.avartar5
    };
    int selectedAvatarResId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdateEmail = findViewById(R.id.etUpdateEmail);
        etUpdatePhone = findViewById(R.id.etUpdatePhone);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);


        layoutAvatarContainer = findViewById(R.id.layoutAvatarContainer);

        dbHelper = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);


        HashMap<String, String> user = dbHelper.getUserById(userId);

        etUpdateName.setText(user.get("name"));
        etUpdateEmail.setText(user.get("email"));
        etUpdatePhone.setText(user.get("phone"));


        String currentImageStr = user.get("image");
        if (currentImageStr != null) {

            selectedAvatarResId = Integer.parseInt(currentImageStr);
        } else {
            selectedAvatarResId = images[0];
        }


        loadAvatars();


        btnUpdateUser.setOnClickListener(v -> {

            dbHelper.updateUser(userId,
                    etUpdateName.getText().toString(),
                    etUpdateEmail.getText().toString(),
                    etUpdatePhone.getText().toString(),
                    selectedAvatarResId);

            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
            finish();
        });
    }


    private void loadAvatars() {
        layoutAvatarContainer.removeAllViews();
        for (int i = 0; i < images.length; i++) {
            int imageResId = images[i];

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
            params.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(params);


            if (imageResId == selectedAvatarResId) {
                imageView.setBackgroundColor(Color.LTGRAY);
                imageView.setPadding(10,10,10,10);
            } else {
                imageView.setBackgroundColor(Color.TRANSPARENT);
                imageView.setPadding(0,0,0,0);
            }

            imageView.setOnClickListener(v -> {
                selectedAvatarResId = imageResId;
                loadAvatars();
            });

            layoutAvatarContainer.addView(imageView);
        }
    }
}