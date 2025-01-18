package com.example.ntc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    private Button updateButton;

    private DatabaseHelper dbHelper;
    private String userId;  // To identify the user (pass this via Intent)

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize EditTexts and Button
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        updateButton = findViewById(R.id.updateButton);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Retrieve userId from Intent (assuming it's passed from MainActivity)
        userId = getIntent().getStringExtra("USER_ID");

        // Load existing user details
        loadUserProfile();

        // Set up update button click listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }

    private void loadUserProfile() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, "id=?", new String[]{userId}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Get values from cursor
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COL_2));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COL_3));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COL_4));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COL_5));

            // Set values in EditTexts
            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            emailEditText.setText(email);
            passwordEditText.setText(password); // Optionally, you might want to hide the password

            cursor.close();
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserProfile() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_COL_2, firstNameEditText.getText().toString());
        values.put(DatabaseHelper.USER_COL_3, lastNameEditText.getText().toString());
        values.put(DatabaseHelper.USER_COL_4, emailEditText.getText().toString());
        values.put(DatabaseHelper.USER_COL_5, passwordEditText.getText().toString());

        // Update user details in the database
        int rowsAffected = db.update(DatabaseHelper.TABLE_USERS, values, "id=?", new String[]{userId});

        if (rowsAffected > 0) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close ProfileActivity and return to previous activity
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}