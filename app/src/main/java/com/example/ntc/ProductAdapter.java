package com.example.ntc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> productList;

    // Constructor
    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_product, parent, false);
        }

        Product currentProduct = (Product) getItem(position);

        // Initialize views
        ImageView productImageView = convertView.findViewById(R.id.product_image);
        TextView nameTextView = convertView.findViewById(R.id.product_name);
        TextView descriptionTextView = convertView.findViewById(R.id.product_description);
        TextView priceTextView = convertView.findViewById(R.id.product_price);
        Button orderButton = convertView.findViewById(R.id.order_button);      // Order button
        //Button viewOrderButton = convertView.findViewById(R.id.view_order_button); // View Orders button

        // Set product data to views
        productImageView.setImageBitmap(currentProduct.getImage());
        nameTextView.setText(currentProduct.getName());
        descriptionTextView.setText(currentProduct.getDescription());
        priceTextView.setText(String.format("Rs. %.2f", currentProduct.getPrice()));

        // Order Button Click Event
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Order button clicked", Toast.LENGTH_SHORT).show();
                // Intent to navigate to OrderActivity
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("product_name", currentProduct.getName());
                intent.putExtra("product_price", currentProduct.getPrice());
                context.startActivity(intent);
            }
        });

        // View Orders Button Click Event
//        viewOrderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Redirecting to View Orders", Toast.LENGTH_SHORT).show();
//                // Intent to navigate to ViewOrdersUse activity
//                Intent intent = new Intent(context, ViewOrdersUse.class);
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }
}
