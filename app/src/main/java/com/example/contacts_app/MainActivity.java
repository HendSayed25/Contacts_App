package com.example.contacts_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView add_btn;
    ContactDataBase db;
    ContactsAdapter adapter;
    RecyclerView recycler;
    SearchView search_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_btn=findViewById(R.id.add);
        recycler=findViewById(R.id.recyclerView);
        search_icon=findViewById(R.id.search);

        db=new ContactDataBase(this,ContactDataBase.TABLE_USER,null,ContactDataBase.DATABASE_VERSION);

        showAllContacts();
        db.retrieveAllData().size();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AddActivity.class);
                startActivity(i);
            }
        });
     /*   search_icon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });*/




    }

    public void showAllContacts(){
        ArrayList<User> users=new ArrayList<>();
        users=db.retrieveAllData();

        adapter=new ContactsAdapter(users,MainActivity.this);
        recycler.setAdapter(adapter);

    }
    public void search(String search_res){
        ArrayList<User>resualt=db.retrieveAllData();
        ArrayList<User>new_res=new ArrayList<>();
        for(int i=0;i<resualt.size();i++){
            if(search_res==resualt.get(i).getName().toLowerCase()||search_res==resualt.get(i).getPhone()){
                new_res.add(resualt.get(i));
            }
        }
        adapter=new ContactsAdapter(new_res,MainActivity.this);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}