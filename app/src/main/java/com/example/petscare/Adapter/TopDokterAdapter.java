package com.example.petscare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petscare.ChatRoom;
import com.example.petscare.Class.Dokter;
import com.example.petscare.R;

import java.util.List;

public class TopDokterAdapter extends RecyclerView.Adapter<TopDokterAdapter.bebas> {
    List<Dokter> getAlls;
    Context context;

    public TopDokterAdapter(List<Dokter> getAlls, Context context) {
        this.getAlls = getAlls;
        this.context = context;
    }

    @NonNull
    @Override
    public bebas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_top_dokter, parent, false);
        return new bebas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bebas holder, int position) {
//        holder.id.setText(getAlls.get(position).getId());
        holder.judul.setText(getAlls.get(position).getNama());
//        holder.isi.setText(getAlls.get(position).getIsi());
        holder.date.setText(getAlls.get(position).getPhone());

        String url = getAlls.get(position).getImage();
        Glide.with(context)
                .load("http://192.168.42.72/:8000/storage/" + url)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ChatRoom.class);
                i.putExtra("id", getAlls.get(position).getId());
                i.putExtra("nama", getAlls.get(position).getNama());
                i.putExtra("phone", getAlls.get(position).getPhone());
                i.putExtra("level", getAlls.get(position).getLevel());

                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return getAlls.size();
    }

    public class bebas extends RecyclerView.ViewHolder {
        TextView id,judul,isi,date;
        ImageView imageView;
        public bebas(@NonNull View itemView) {
            super(itemView);
//            id = itemView.findViewById(R.id.id);
            judul = itemView.findViewById(R.id.ct1);
//            isi = itemView.findViewById(R.id.isi);
            date= itemView.findViewById(R.id.ct2);
            imageView = itemView.findViewById(R.id.img_ct0);
        }
    }
}
