package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petscare.Adapter.DokterAdapter;
import com.example.petscare.Adapter.TopDokterAdapter;
import com.example.petscare.Class.Dokter;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    List<Dokter> dokter_List;
    TopDokterAdapter topDokterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView textView = findViewById(R.id.title_id);
        recyclerView = findViewById(R.id.recy_top_doktor);
        layoutManager    = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatActivity.this, News.class));
                finish();
            }
        });

        all_dokter();



    }

    private void all_dokter(){


        try {
            Interfaces cl = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Dokter>> getAll = cl.getDok();
            getAll.enqueue(new Callback<List<Dokter>>() {
                @Override
                public void onResponse(Call<List<Dokter>> call, Response<List<Dokter>> response) {
                    List<Dokter> values = response.body();
                    if (response.isSuccessful() && response.body()!=null){

                        //Toast.makeText(getApplicationContext(), "" + values.get(0).getLevel(), Toast.LENGTH_SHORT).show();
                        dokter_List = response.body();
                        topDokterAdapter = new TopDokterAdapter(dokter_List, ChatActivity.this);
                        recyclerView.setAdapter(topDokterAdapter);
                        topDokterAdapter.notifyDataSetChanged();
                        //mShimmerViewContainer.stopShimmerAnimation();
                        //mShimmerViewContainer.setVisibility(View.GONE);


                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Dokter>> call, Throwable t) {
                    Log.e("Retrofit error", String.valueOf(t));
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Baris ini adalah Finally yang dieksekusi");
        }
        System.out.println("Akhir Program");
    }
}