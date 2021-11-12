package com.example.petscare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petscare.Class.Artikel;
import com.example.petscare.DetailArtikel;
import com.example.petscare.R;

import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.myartikel> {

    List<Artikel> artikelList;
    Context context;
    String url = "storage/";

    public ArtikelAdapter(List<Artikel> artikelList, Context context) {
        this.artikelList = artikelList;
        this.context = context;
    }

    @NonNull
    @Override
    public myartikel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_article, parent, false);
        return new myartikel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myartikel holder, int position) {
        holder.nama_gejala.setText(artikelList.get(position).getTitle());
        holder.jenis.setText(artikelList.get(position).getJenis());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, DetailArtikel.class);
                i.putExtra("id", artikelList.get(position).getId());
                i.putExtra("title", artikelList.get(position).getTitle());
                i.putExtra("jenis", artikelList.get(position).getJenis());
                i.putExtra("isi", artikelList.get(position).getIsi_content());
                i.putExtra("image", artikelList.get(position).getImage());
                i.putExtra("created_at", artikelList.get(position).getCreated_at());
                i.putExtra("updated_id", artikelList.get(position).getUpdated_at());
                context.startActivity(i);

            }
        });


        url = artikelList.get(position).getImage();
        Glide.with(context)
                .load("http://192.168.42.95/:8000/storage/" + url)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return artikelList.size();
    }

    public class myartikel extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nama_gejala,jenis;
        public myartikel(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            nama_gejala = itemView.findViewById(R.id.nama_gejala);
            jenis = itemView.findViewById(R.id.jenis);
        }
    }
}
