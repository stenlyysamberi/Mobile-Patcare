package com.example.petscare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petscare.Class.Pesan;
import com.example.petscare.R;

import java.util.ArrayList;

public class PesanAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Pesan> pesanArrayList;

    int ITEM_SEND = 1;
    int ITEM_RECIEVE = 2;


    public PesanAdapter(Context context, ArrayList<Pesan> pesanArrayList) {
        this.context = context;
        this.pesanArrayList = pesanArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.recieverchatlayout,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Pesan messages = pesanArrayList.get(position);
        if(holder.getClass() == SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
        }
        else
        {
            RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView textViewmessaage;
        TextView timeofmessage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);

        }
    }

    class RecieverViewHolder extends  RecyclerView.ViewHolder{
        TextView textViewmessaage;
        TextView timeofmessage;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }




}
