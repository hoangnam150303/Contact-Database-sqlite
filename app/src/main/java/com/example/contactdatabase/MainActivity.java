package com.example.contactdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvUserList;
    Button btnAddUser;
    DatabaseHelper dbHelper;
    UserAdapter adapter;
    ArrayList<HashMap<String, String>> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUserList = findViewById(R.id.rvUserList);
        btnAddUser = findViewById(R.id.btnAddUser);

        dbHelper = new DatabaseHelper(this);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));

        loadUserList();

        btnAddUser.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateUserActivity.class));
        });
    }

    private void loadUserList() {
        userList = dbHelper.getAllUsers();
        adapter = new UserAdapter(this, userList);
        rvUserList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserList(); // refresh list
    }
}
