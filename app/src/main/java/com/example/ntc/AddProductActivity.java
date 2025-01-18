package com.example.ntc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nameEditText, descriptionEditText, priceEditText;
    private ImageView productImageView;
    private Button selectImageButton, addButton, viewOrdersButton, viewUsersButton; // Include viewOrdersButton
    private Bitmap productImageBitmap;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize views
        nameEditText = findViewById(R.id.product_name);
        descriptionEditText = findViewById(R.id.product_description);
        priceEditText = findViewById(R.id.product_price);
        productImageView = findViewById(R.id.product_image);
        selectImageButton = findViewById(R.id.select_image_button);
        addButton = findViewById(R.id.add_product_button);
        viewOrdersButton = findViewById(R.id.view_orders_button); // Initialize View Orders button
        viewUsersButton= findViewById(R.id.view_users_button);
        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set select image button click listener
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });


        // Set add product button click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        // Set view orders button click listener
        viewOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ViewOrdersActivity.class);
                startActivity(intent);
            }
        });
        viewUsersButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddProductActivity.this, ViewUsersActivity.class);
            startActivity(intent);
        });

    }

    // Method to open the image picker
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                productImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                productImageView.setImageBitmap(productImageBitmap); // Set the selected image in the ImageView
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to add the product
    private void addProduct() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || priceString.isEmpty() || productImageBitmap == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceString);

        // Convert the bitmap image to a byte array
        byte[] imageBytes = getBytesFromBitmap(productImageBitmap);

        // Insert the product into the database
        long result = databaseHelper.addProduct(name, description, price, imageBytes);
        if (result != -1) {
            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
            clearForm();
        } else {
            Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to convert Bitmap to byte array
    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // Method to clear the form after successful product addition
    private void clearForm() {
        nameEditText.setText("");
        descriptionEditText.setText("");
        priceEditText.setText("");
        productImageView.setImageResource(0);
        productImageBitmap = null;
    }
}
