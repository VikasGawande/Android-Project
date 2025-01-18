package com.example.ntc;

import static com.example.ntc.DatabaseHelper.ORDER_COL_1;
import static com.example.ntc.DatabaseHelper.ORDER_COL_2;
import static com.example.ntc.DatabaseHelper.ORDER_COL_4;
import static com.example.ntc.DatabaseHelper.ORDER_COL_5;
import static com.example.ntc.DatabaseHelper.ORDER_COL_6;
import static com.example.ntc.DatabaseHelper.ORDER_COL_7;
import static com.example.ntc.DatabaseHelper.ORDER_COL_8;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewOrdersActivity extends AppCompatActivity {

    private TextView ordersTextView;
    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        ordersTextView = findViewById(R.id.ordersTextView);
        dbHelper = new DatabaseHelper(this);

        displayOrders();
    }

    @SuppressLint("Range")
    private void displayOrders() {
        Cursor cursor = dbHelper.getAllOrders();
        StringBuilder ordersList = new StringBuilder();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ordersList.append("Order ID: ").append(cursor.getInt(cursor.getColumnIndex(ORDER_COL_1)))
                        .append("\nUsername: ").append(cursor.getString(cursor.getColumnIndex(ORDER_COL_2)))
                        .append("\nProduct Name: ").append(cursor.getString(cursor.getColumnIndex(ORDER_COL_5)))
                        .append("\nQuantity: ").append(cursor.getInt(cursor.getColumnIndex(ORDER_COL_7)))
                        .append("\nPrice: ").append(cursor.getDouble(cursor.getColumnIndex(ORDER_COL_6)))
                        .append("\nPayment Type: ").append(cursor.getString(cursor.getColumnIndex(ORDER_COL_8)))
                        .append("\nDelivery Location: ").append(cursor.getString(cursor.getColumnIndex(ORDER_COL_4)))
                        .append("\n\n");
            }
            ordersTextView.setText(ordersList.toString());
        } else {
            ordersTextView.setText("No orders found");
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
