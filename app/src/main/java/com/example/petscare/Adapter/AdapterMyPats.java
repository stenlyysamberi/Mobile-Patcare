package com.example.petscare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petscare.Class.MyPet;
import com.example.petscare.MyPat;
import com.example.petscare.R;

import java.util.List;

public class AdapterMyPats extends RecyclerView.Adapter<AdapterMyPats.viewHolder> {


    List<MyPet> myPets;
    Context context;

    public AdapterMyPats(List<MyPet> myPets, Context context) {
        this.myPets = myPets;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_maypats, parent, false);
        return new  viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.nama.setText(myPets.get(position).getNama());
        holder.jenis.setText(myPets.get(position).getJenis());
        holder.umur.setText( myPets.get(position).getUmur() + " " + "months old");
        holder.ttl.setText(myPets.get(position).getTtl());

    }

    @Override
    public int getItemCount() {
        return myPets.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView nama,jenis,umur,ttl;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nama  = itemView.findViewById(R.id.nama_mypats);
            jenis = itemView.findViewById(R.id.jenis_myPats);
            umur  = itemView.findViewById(R.id.umur_myPats);
            ttl   = itemView.findViewById(R.id.ttl_myPats);

        }
    }
}
