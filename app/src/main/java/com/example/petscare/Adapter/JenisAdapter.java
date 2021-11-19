package com.example.petscare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petscare.Class.Jenis;
import com.example.petscare.Medical;
import com.example.petscare.R;

import java.util.List;

public class JenisAdapter extends RecyclerView.Adapter<JenisAdapter.viewHolder> {

    List<Jenis> jenis;
    Context mcontex;

    public JenisAdapter(List<Jenis> jenis, Context mcontex) {
        this.jenis = jenis;
        this.mcontex = mcontex;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontex).inflate(R.layout.row_gejala, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.jenis.setText(jenis.get(position).getJenis());

    }

    @Override
    public int getItemCount() {
        return jenis.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView jenis;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            jenis = itemView.findViewById(R.id.id_gejala_sheet);
        }
    }
}
