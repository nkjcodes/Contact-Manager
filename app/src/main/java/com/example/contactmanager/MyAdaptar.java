package com.example.contactmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaptar extends RecyclerView.Adapter<MyAdaptar.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public MyAdaptar(Context context,ArrayList<Model> mList){
        this.mList=mList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contact,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, phone, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_txt);
            phone = itemView.findViewById(R.id.phone_txt);
            email = itemView.findViewById(R.id.email_txt);

        }
    }
}
