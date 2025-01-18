package com.example.ntc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;
    ImageView logo; // Declare the logo ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        button1 = findViewById(R.id.getstart1);
        button2 = findViewById(R.id.getstart2);
        logo = findViewById(R.id.logo); // Initialize the logo ImageView

        // Assuming you have a method to retrieve the current user's ID
        String currentUserId = "1"; // Example user ID, replace with actual logic to get the current user's ID

        // Set click listener on the logo
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("USER_ID", currentUserId); // Pass the user ID
                startActivity(intent);
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,Secondcitivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SecondActivity2.class);
                startActivity(intent);
            }
        });
    }



    public void beforeage18(View view) {
        Intent intent = new Intent(MainActivity.this, Secondcitivity.class);
        startActivity(intent);
    }

    public void afterage18(View view) {
        Intent intent1 = new Intent(MainActivity.this, SecondActivity2.class);
        startActivity(intent1);
    }

    public void food(View view) {
        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
        startActivity(intent);
    }



}