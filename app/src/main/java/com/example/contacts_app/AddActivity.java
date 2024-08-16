package com.example.contacts_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText name,phoneNumber;
    Button add_btn;
    User user;
    ContactDataBase db;
    ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=findViewById(R.id.user_name_edt);
        phoneNumber=findViewById(R.id.phone_edt);
        add_btn=findViewById(R.id.add_btn_to_database);

        db=new ContactDataBase(this,ContactDataBase.TABLE_USER,null,ContactDataBase.DATABASE_VERSION);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber.getText().toString().matches("01[0-9]{9}")) {
                    user = new User(name.getText().toString(), phoneNumber.getText().toString());
                    if (db.insertUser(user)) {
                        Toast.makeText(AddActivity.this, "Added Successfully", Toast.LENGTH_LONG).show();
                        adapter.users.add(user);
                        Intent i=new Intent(AddActivity.this,MainActivity.class);
                        startActivity(i);

                    } else {
                        Log.e("add", "error");
                    }

                } else{
                    phoneNumber.setError("Please Enter valid Phone Number");
                }
            }
        });
    }
}