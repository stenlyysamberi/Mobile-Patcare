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
import com.example.petscare.Class.Balai;
import com.example.petscare.DetailArtikel;
import com.example.petscare.R;

import java.util.List;

public class BalaiAdapter extends RecyclerView.Adapter<BalaiAdapter.viewHolder> {
    List<Balai> balais;
    Context context;

    public BalaiAdapter(List<Balai> balais, Context context) {
        this.balais = balais;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_balai, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.judul_balai.setText(balais.get(position).getJudul());
        holder.tgl_tulis.setText(balais.get(position).getCreated_at());

        String url = balais.get(position).getImg_p();
        Glide.with(context)
                .load("http://192.168.42.95/:8000/storage/" + url)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.img_balai);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, DetailArtikel.class);
                i.putExtra("id", balais.get(position).getId());
                i.putExtra("title", balais.get(position).getJudul());
                //i.putExtra("jenis", balais.get(position).getJenis());
                i.putExtra("isi", balais.get(position).getIsi());
                i.putExtra("image", balais.get(position).getImg_p());
                i.putExtra("created_at", balais.get(position).getCreated_at());
                i.putExtra("updated_id", balais.get(position).getUpdated_at());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return balais.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView img_balai;
        TextView judul_balai,tgl_tulis,penulis;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img_balai = itemView.findViewById(R.id.img_balai);
            judul_balai = itemView.findViewById(R.id.judul_balai);
            tgl_tulis   = itemView.findViewById(R.id.tgl_tulis);
            penulis     = itemView.findViewById(R.id.penulis);
        }
    }
}
