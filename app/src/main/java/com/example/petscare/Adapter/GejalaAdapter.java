package com.example.petscare.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petscare.Class.Gejala;
import com.example.petscare.R;

import java.util.List;

public class GejalaAdapter extends RecyclerView.Adapter<GejalaAdapter.viewHolder> {

    List<Gejala> gejalaList;
    Context context;

    public GejalaAdapter(List<Gejala> gejalaList, Context context) {
        this.gejalaList = gejalaList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_gejala, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.gejala_sheet.setText(gejalaList.get(position).getNama_gejala());

    }

    @Override
    public int getItemCount() {
        return gejalaList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gejala_sheet;
        private SparseBooleanArray selectItems = new SparseBooleanArray();
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            gejala_sheet = itemView.findViewById(R.id.id_gejala_sheet);
        }


        @Override
        public void onClick(View view) {
            if (selectItems.get(getAdapterPosition(),false)){
                selectItems.delete(getAdapterPosition());
                view.setSelected(false);
            }else{
                selectItems.put(getAdapterPosition(),true);
                view.setSelected(true);
            }
        }
    }
}
