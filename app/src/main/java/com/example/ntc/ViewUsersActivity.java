 package com.example.ntc;

 import android.annotation.SuppressLint;
 import android.database.Cursor;
 import android.os.Bundle;
 import android.widget.ListView;
 import android.widget.SimpleAdapter;
 import android.widget.Toast;
 import androidx.appcompat.app.AppCompatActivity;
 import java.util.ArrayList;
 import java.util.HashMap;

 public class ViewUsersActivity extends AppCompatActivity {

     DatabaseHelper myDb;
     ListView listView;
     ArrayList<HashMap<String, String>> userList;

     @SuppressLint("MissingInflatedId")
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_view_users);

         myDb = new DatabaseHelper(this);
         listView = findViewById(R.id.user_list);
         userList = new ArrayList<>();

         loadUsers();
     }

     private void loadUsers() {
         Cursor res = myDb.getAllUsers();
         if (res.getCount() == 0) {
             Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
             return;
         }

         while (res.moveToNext()) {
             // Create a HashMap for each user and add it to the userList
             HashMap<String, String> user = new HashMap<>();
             user.put("email", "Email: " + res.getString(3));
             user.put("name", "Name: " + res.getString(1) + " " + res.getString(2));

             userList.add(user);
         }

         // Use a SimpleAdapter to display the list of users
         SimpleAdapter adapter = new SimpleAdapter(
                 this,
                 userList,
                 android.R.layout.simple_list_item_2,
                 new String[]{"email", "name"},
                 new int[]{android.R.id.text1, android.R.id.text2}
         );

         listView.setAdapter(adapter);
     }
 }