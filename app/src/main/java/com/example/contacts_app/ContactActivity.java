package com.example.contacts_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class ContactActivity extends AppCompatActivity {
    EditText username,phoneNumber;
    Button update_btn;
    ImageView call,messsage,delete,edit;
    ContactDataBase db;
    String oldPhone;
    ContactsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();
        getDataFromIntent();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_btn.setVisibility(View.VISIBLE);
                oldPhone=phoneNumber.getText().toString();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String phone=phoneNumber.getText().toString();
                if(valid(name,phone)){

                   boolean res= db.updateUser(new User(name,phone),oldPhone); //review this point how to update
                    if(res){
                        Toast.makeText(ContactActivity.this,"Updated Successfully",Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                    }

                }
                update_btn.setVisibility(View.INVISIBLE);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show message to user to ensure if need to delete or not
                AlertDialog.Builder alert = new AlertDialog.Builder(ContactActivity.this);
                alert.setTitle("Delete contact?");
                alert.setMessage("Are you sure to delete this contact");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        dialog.dismiss();
                        boolean res=db.deleteData(phoneNumber.getText().toString());
                        if(res){
                            Toast.makeText(ContactActivity.this,"Deleted Successfully",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(ContactActivity.this,MainActivity.class);
                            adapter.notifyDataSetChanged();
                            startActivity(i);

                        }else{
                            Toast.makeText(ContactActivity.this,"something wrong please try later",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:" + phoneNumber.getText().toString();
                intent.setData(Uri.parse(temp));
                startActivity(intent);
            }
        });
        messsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("sms:"+phoneNumber.getText().toString()));
                smsIntent.putExtra("sms_body", "Hello "+username.getText().toString());
                startActivity(smsIntent);
            }
        });


    }
    public void init(){
        username = findViewById(R.id.user_name_contact);
        phoneNumber = findViewById(R.id.phoneNumber_contact);
        update_btn=findViewById(R.id.update_btn);
        call=findViewById(R.id.call_number);
        messsage=findViewById(R.id.messsage_number);
        delete=findViewById(R.id.delete_contact);
        edit=findViewById(R.id.update_contact);
        db=new ContactDataBase(this,ContactDataBase.TABLE_USER,null,ContactDataBase.DATABASE_VERSION);
    }
    public Boolean valid(String name,String phone){
        boolean valid=true;

        if(name.isEmpty()){
            username.setError("Please Fill it correctly");
            valid=false;
        }
        if(phone.isEmpty()||(!phone.matches("01[0-9]{9}"))){
            phoneNumber.setError("Please Fill it correctly");
            valid=false;
        }
        return valid;
    }
    public void getDataFromIntent(){
        String name=getIntent().getStringExtra("name");
        Log.e("name",name);
        ArrayList<User> users=db.retrieveAllData();
        for(int i=0;i< users.size();i++){
            if(Objects.equals(users.get(i).getName(), name)){
                username.setText(users.get(i).getName());
                phoneNumber.setText(users.get(i).getPhone());
                break;
            }
        }
    }

}