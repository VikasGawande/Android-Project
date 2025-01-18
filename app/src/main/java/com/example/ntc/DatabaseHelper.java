package com.example.ntc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "appDatabase.db";
    private static final int DATABASE_VERSION = 9; // Incremented version to add new table

    // Users table name and columns
    public static final String TABLE_USERS = "users";
    public static final String USER_COL_1 = "id";         // User ID column
    public static final String USER_COL_2 = "first_name"; // First name column
    public static final String USER_COL_3 = "last_name";  // Last name column
    public static final String USER_COL_4 = "email";      // Email column
    public static final String USER_COL_5 = "password";   // Password column

    // Products table name and columns
    private static final String TABLE_PRODUCTS = "products";
    public static final String PRODUCT_COL_1 = "id";          // Product ID column
    public static final String PRODUCT_COL_2 = "name";        // Product name
    public static final String PRODUCT_COL_3 = "description"; // Product description
    public static final String PRODUCT_COL_4 = "price";       // Product price
    public static final String PRODUCT_COL_5 = "image";       // Product image (as BLOB)

    // Orders table name and columns
    private static final String TABLE_ORDERS = "orders";
    public static final String ORDER_COL_1 = "id";                   // Order ID column
    public static final String ORDER_COL_2 = "username";             // Username
    public static final String ORDER_COL_3 = "email";                // User email
    public static final String ORDER_COL_4 = "delivery_address";     // Delivery address
    public static final String ORDER_COL_5 = "product_name";         // Product name
    public static final String ORDER_COL_6 = "product_price";        // Product price
    public static final String ORDER_COL_7 = "quantity";             // Order quantity
    public static final String ORDER_COL_8 = "payment_method";       // Payment method

    // SQL query to create the Users table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + USER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COL_2 + " TEXT NOT NULL, "
            + USER_COL_3 + " TEXT NOT NULL, "
            + USER_COL_4 + " TEXT NOT NULL UNIQUE, "
            + USER_COL_5 + " TEXT NOT NULL" + ")";

    // SQL query to create the Products table
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + PRODUCT_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PRODUCT_COL_2 + " TEXT NOT NULL, "
            + PRODUCT_COL_3 + " TEXT NOT NULL, "
            + PRODUCT_COL_4 + " REAL NOT NULL, "
            + PRODUCT_COL_5 + " BLOB NOT NULL" + ")";

    // SQL query to create the Orders table
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + "("
            + ORDER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ORDER_COL_2 + " TEXT NOT NULL, "
            + ORDER_COL_3 + " TEXT NOT NULL, "
            + ORDER_COL_4 + " TEXT NOT NULL, "
            + ORDER_COL_5 + " TEXT NOT NULL, "
            + ORDER_COL_6 + " REAL NOT NULL, "
            + ORDER_COL_7 + " INTEGER NOT NULL, "
            + ORDER_COL_8 + " TEXT NOT NULL" + ")";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Create the Users, Products, and Orders tables
            db.execSQL(CREATE_TABLE_USERS);
            db.execSQL(CREATE_TABLE_PRODUCTS);
            db.execSQL(CREATE_TABLE_ORDERS);
            Log.d(TAG, "Tables created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error creating tables", e);
        }
    }

    // Called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // Drop the old tables if they exist
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);

            // Recreate the tables
            onCreate(db);
        } catch (Exception e) {
            Log.e(TAG, "Error upgrading database", e);
        }
    }

    // Method to add a user
    public boolean addUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COL_2, firstName);
        values.put(USER_COL_3, lastName);
        values.put(USER_COL_4, email);
        values.put(USER_COL_5, password);

        // Insert the new user into the database
        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        return result != -1;
    }

    // Method to check if the user exists in the database
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_COL_1}; // We only need to check if a record exists
        String selection = USER_COL_4 + " = ? AND " + USER_COL_5 + " = ?";
        String[] selectionArgs = {email, password};

        // Query the database to check if a user exists with the given email and password
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    // Method to add a product
    public long addProduct(String name, String description, double price, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COL_2, name);
        values.put(PRODUCT_COL_3, description);
        values.put(PRODUCT_COL_4, price);
        values.put(PRODUCT_COL_5, image);

        // Insert the new product into the database
        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();

        return result;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    // Method to add an order
    public long addOrder(String username, String email, String deliveryAddress, String productName,
                         double productPrice, int quantity, String paymentMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_COL_2, username);
        values.put(ORDER_COL_3, email);
        values.put(ORDER_COL_4, deliveryAddress);
        values.put(ORDER_COL_5, productName);
        values.put(ORDER_COL_6, productPrice);
        values.put(ORDER_COL_7, quantity);
        values.put(ORDER_COL_8, paymentMethod);

        // Insert the new order into the database
        long result = db.insert(TABLE_ORDERS, null, values);
        db.close();

        return result;
    }

    // Method to get all orders (optional)
    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM users", null);
    }
}
