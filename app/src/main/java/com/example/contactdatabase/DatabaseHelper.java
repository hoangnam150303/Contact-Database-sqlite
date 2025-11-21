package com.example.contactdatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_PHONE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Thêm người dùng
    public void addUser(String name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE, phone);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // Lấy toàn bộ người dùng
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<>();
                user.put("id", cursor.getString(0));
                user.put("name", cursor.getString(1));
                user.put("email", cursor.getString(2));
                user.put("phone", cursor.getString(3));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    // Lấy user theo ID
    public HashMap<String, String> getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE id=" + id, null);
        HashMap<String, String> user = new HashMap<>();

        if (cursor.moveToFirst()) {
            user.put("id", cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("phone", cursor.getString(3));
        }
        cursor.close();
        return user;
    }

    // Cập nhật user
    public void updateUser(int id, String name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE, phone);
        db.update(TABLE_USER, values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Xoá user
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
