package com.example.ntc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private EditText usernameInput, emailInput, addressInput, quantityInput;
    private RadioButton cashOnDelivery;
    private Button confirmOrderButton;
    private TextView productNameTextView, productPriceTextView; // TextViews to show product details
    private DatabaseHelper databaseHelper; // Reference to DatabaseHelper

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize input fields
        usernameInput = findViewById(R.id.username);
        emailInput = findViewById(R.id.email);
        addressInput = findViewById(R.id.delivery_address);
        quantityInput = findViewById(R.id.quantity);
        cashOnDelivery = findViewById(R.id.cash_on_delivery);
        confirmOrderButton = findViewById(R.id.confirm_order_button);

        // Initialize TextViews for product details
        productNameTextView = findViewById(R.id.product_name);
        productPriceTextView = findViewById(R.id.product_price);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Get product name and price passed from the previous activity
        String productName = getIntent().getStringExtra("product_name");
        double productPrice = getIntent().getDoubleExtra("product_price", 0.0);

        // Display product name and price
        productNameTextView.setText(productName);
        productPriceTextView.setText(String.format("Ru.2f", productPrice));

        // Handle Confirm Order button click
        // Handle Confirm Order button click
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String email = emailInput.getText().toString();
                String address = addressInput.getText().toString();
                String quantityString = quantityInput.getText().toString();

                if (username.isEmpty() || email.isEmpty() || address.isEmpty() || quantityString.isEmpty()) {
                    Toast.makeText(OrderActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate quantity input
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityString);
                    if (quantity <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(OrderActivity.this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save order details to the database
                long orderResult = databaseHelper.addOrder(username, email, address, productName, productPrice, quantity, "Cash on Delivery");

                if (orderResult != -1) {
                    Toast.makeText(OrderActivity.this, "Order placed for " + quantity + " units of " + productName + "!", Toast.LENGTH_LONG).show();

                    // Clear the form after successful order
                    usernameInput.setText("");
                    emailInput.setText("");
                    addressInput.setText("");
                    quantityInput.setText("");
                    cashOnDelivery.setChecked(false);  // Reset the radio button if needed
                } else {
                    Toast.makeText(OrderActivity.this, "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
