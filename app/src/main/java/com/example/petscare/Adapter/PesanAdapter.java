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
import com.example.petscare.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PesanAdapter extends RecyclerView.Adapter {
    SessionManager sessionManager;


    List<Pesan> pesanList;
    Context context;

    int ITEM_SEND = 1;
    int ITEM_RECIEVE = 2;


    public PesanAdapter(List<Pesan> pesanList, Context context) {
        this.pesanList = pesanList;
        this.context = context;
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

        Pesan messages = pesanList.get(position);
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
        sessionManager = new SessionManager(context.getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String id = user.get(SessionManager.kunci_id);
        Pesan pesan = pesanList.get(position);

        if (id.equals(pesan.getSenderId())){
            return ITEM_SEND;
        }else{
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return pesanList.size();
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
