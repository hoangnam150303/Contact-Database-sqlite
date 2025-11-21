package com.example.contactdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {
    EditText etName, etEmail, etPhone;
    Button btnSaveUser;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSaveUser = findViewById(R.id.btnSaveUser);

        dbHelper = new DatabaseHelper(this);

        btnSaveUser.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.addUser(name, email, phone);
            Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
