package com.example.helpingpeople;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodUser> list;

    public FoodAdapter(Context context, ArrayList<FoodUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.fooditem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FoodUser foodUser=list.get(position);
        holder.fname.setText(foodUser.getName());
        holder.faddr.setText(foodUser.getAddress());
        holder.fdonation.setText(foodUser.getDonation());
        holder.fesqt.setText(foodUser.getQuantity());
        holder.fcontact.setText(foodUser.getPhoneNo());
        holder.femail.setText(foodUser.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fname,faddr,fdonation,fesqt,fcontact,femail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fname=itemView.findViewById(R.id.cname);
            faddr=itemView.findViewById(R.id.caddr);
            fdonation=itemView.findViewById(R.id.ctype);
            fesqt=itemView.findViewById(R.id.cdonation);
            fcontact=itemView.findViewById(R.id.ccontact);
            femail=itemView.findViewById(R.id.cemail);
        }
    }
}
