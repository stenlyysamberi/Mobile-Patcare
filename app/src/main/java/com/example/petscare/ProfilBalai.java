package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.petscare.Adapter.BalaiAdapter;
import com.example.petscare.Class.Balai;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilBalai extends AppCompatActivity {
//    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//    int notifID = 1;
    private ShimmerFrameLayout mShimmerViewContainer;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BalaiAdapter balai_Adapter;
    List<Balai> balai_list;
    Context context;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_balai);
        
        back = findViewById(R.id.title_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilBalai.this, News.class));
                finish();
            }
        });

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        recyclerView = (RecyclerView) findViewById(R.id.recy_balai);
        layoutManager    = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        get_all_balai();

    }

    private void get_all_balai(){
        try {

            Interfaces balai = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Balai>> myBalai = balai.getBalai();
            myBalai.enqueue(new Callback<List<Balai>>() {
                @Override
                public void onResponse(Call<List<Balai>> call, Response<List<Balai>> response) {
                    //List<Balai> values = response.body();
                    //Toast.makeText(getApplicationContext(), "" + values.get(0).getIsi(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful() && response.body() != null){
                        if(response.code() == 200){
                            balai_list = response.body();
                            balai_Adapter = new BalaiAdapter(balai_list, ProfilBalai.this);
                            recyclerView.setAdapter(balai_Adapter);
                            balai_Adapter.notifyDataSetChanged();
                        }

                        mShimmerViewContainer.stopShimmerAnimation();
                        //mShimmerViewContainer.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<List<Balai>> call, Throwable t) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Baris ini adalah Finally yang dieksekusi");
        }
        System.out.println("Akhir Program");


    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


}