package com.example.helpingpeople;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class dishaAdapter extends RecyclerView.Adapter<dishaAdapter.MyViewHolder> {

    Context context;
    ArrayList<dishaUser> list;

    public dishaAdapter(Context context, ArrayList<dishaUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public dishaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.dishaitem,parent,false);
        return new dishaAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull dishaAdapter.MyViewHolder holder, int position) {

        dishaUser dishaUser=list.get(position);
        holder.name.setText(dishaUser.getName());
        holder.address.setText(dishaUser.getAddress());
        holder.donation.setText(dishaUser.getDonation());
        holder.quantity.setText(dishaUser.getQuantity());
        holder.phoneNo.setText(dishaUser.getPhoneNo());
        holder.email.setText(dishaUser.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,address,donation,quantity,phoneNo,email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cname);
            address=itemView.findViewById(R.id.caddr);
            donation=itemView.findViewById(R.id.ctype);
            quantity=itemView.findViewById(R.id.cdonation);
            phoneNo=itemView.findViewById(R.id.ccontact);
            email=itemView.findViewById(R.id.cemail);
        }
    }
}
