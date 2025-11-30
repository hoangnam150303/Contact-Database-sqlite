package com.example.contactdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {
    EditText etName, etEmail, etPhone;
    Button btnSaveUser;
    DatabaseHelper dbHelper;
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
        setContentView(R.layout.create_user);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSaveUser = findViewById(R.id.btnSaveUser);
        layoutAvatarContainer = findViewById(R.id.layoutAvatarContainer);

        dbHelper = new DatabaseHelper(this);


        if (images.length > 0) {
            selectedAvatarResId = images[0];
        }


        loadAvatars();

        btnSaveUser.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show();
                return;
            }
            dbHelper.addUser(name, email, phone, selectedAvatarResId);
            Toast.makeText(this, "Created User with Avatar", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadAvatars() {
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