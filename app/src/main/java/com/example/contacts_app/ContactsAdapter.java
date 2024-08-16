package com.example.contacts_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {
    ArrayList<User>users;
    Context context;

    public ContactsAdapter(ArrayList<User> users,Context context) {
        this.users = users;
        this.context = context;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name_contact);

        }
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//inflate means reading the layout xml and translate them into code
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_design,parent,false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        User user=users.get(position);
        holder.name.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ContactActivity.class);
                i.putExtra("name",user.getName());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }



}
