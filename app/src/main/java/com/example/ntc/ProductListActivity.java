package com.example.ntc;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    private ListView productListView;
    private DatabaseHelper databaseHelper;
    private ArrayList<Product> productList;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productListView = findViewById(R.id.product_list_view);
        databaseHelper = new DatabaseHelper(this);
        productList = new ArrayList<>();

        // Load all products from the database
        loadProducts();

        // Set the adapter to display products in the ListView
        productAdapter = new ProductAdapter(this, productList);
        productListView.setAdapter(productAdapter);
    }

    private void loadProducts() {
        Cursor cursor = databaseHelper.getAllProducts();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No products available", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            double price = cursor.getDouble(3);
            byte[] imageBytes = cursor.getBlob(4);

            // Convert byte[] to Bitmap
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            // Add product to the list
            productList.add(new Product(id, name, description, price, image));
        }
        cursor.close();
    }
}
