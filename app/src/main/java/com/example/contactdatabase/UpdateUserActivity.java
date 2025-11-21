package com.example.contactdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateUserActivity extends AppCompatActivity {
    EditText etUpdateName, etUpdateEmail, etUpdatePhone;
    Button btnUpdateUser;
    DatabaseHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdateEmail = findViewById(R.id.etUpdateEmail);
        etUpdatePhone = findViewById(R.id.etUpdatePhone);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);

        dbHelper = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);
        HashMap<String, String> user = dbHelper.getUserById(userId);

        etUpdateName.setText(user.get("name"));
        etUpdateEmail.setText(user.get("email"));
        etUpdatePhone.setText(user.get("phone"));

        btnUpdateUser.setOnClickListener(v -> {
            dbHelper.updateUser(userId,
                    etUpdateName.getText().toString(),
                    etUpdateEmail.getText().toString(),
                    etUpdatePhone.getText().toString());
            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
