package com.example.ntc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, register;
    private DatabaseHelper databaseHelper;

    // Admin credentials
    private final String ADMIN_USERNAME = "Admin";
    private final String ADMIN_PASSWORD = "Admin@123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        register = findViewById(R.id.btnsignup);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set register button click listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this, Register.class);
                startActivity(intent1);
                finish();
            }
        });

        // Set login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Basic validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the credentials match the admin login
        if (email.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            Toast.makeText(this, "Admin login successful", Toast.LENGTH_SHORT).show();

            // Navigate to AddProductActivity
            Intent intent = new Intent(LoginActivity.this, AddProductActivity.class);
            startActivity(intent);
        } else {
            // Check if the credentials are valid for a regular user
            boolean isValid = databaseHelper.checkUser(email, password);
            if (isValid) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                // Navigate to the next activity (e.g., MainActivity for regular users)
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
