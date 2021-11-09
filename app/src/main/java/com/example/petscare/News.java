package com.example.petscare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petscare.Adapter.ArtikelAdapter;
import com.example.petscare.Adapter.DokterAdapter;
import com.example.petscare.Class.Artikel;
import com.example.petscare.Class.Dokter;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.example.petscare.SessionManager.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News extends AppCompatActivity {
    private ShimmerFrameLayout mShimmerViewContainer;
    RecyclerView recyclerView,recyclerView_artikel;
    RecyclerView.LayoutManager layoutManager, layoutManager_artikel;
    DokterAdapter product_Adapter;
    List<Dokter> dokter_List;

    SessionManager sessionManager;

    ImageView menuall;



    ArtikelAdapter artikel_Adapter;
    List<Artikel> artikel_List;
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        sessionManager = new SessionManager(getApplicationContext());
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        menuall = findViewById(R.id.menu_all);

        recyclerView = findViewById(R.id.recy_dokter);
        recyclerView_artikel = findViewById(R.id.recy_artikel);

        layoutManager    = new LinearLayoutManager(this);
        layoutManager_artikel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
        recyclerView.setLayoutManager(layoutManager);
        recyclerView_artikel.setLayoutManager(layoutManager_artikel);
//
        recyclerView.setHasFixedSize(true);
        recyclerView_artikel.setHasFixedSize(true);

        menuall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model_menu();
            }
        });

        all_artikel();
        all_dokter();


    }

    private void all_artikel() {
        try {

            Interfaces artikel = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Artikel>> myArtikel = artikel.getArtikel();
            myArtikel.enqueue(new Callback<List<Artikel>>() {
                @Override
                public void onResponse(Call<List<Artikel>> call, Response<List<Artikel>> response) {
                    List<Artikel> values = response.body();
                    //Toast.makeText(getApplicationContext(), "" + values.get(0).getIsi(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful() && response.body() != null){
                        artikel_List = response.body();
                        artikel_Adapter = new ArtikelAdapter(artikel_List, News.this);
                        recyclerView_artikel.setAdapter(artikel_Adapter);
                        artikel_Adapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<List<Artikel>> call, Throwable t) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Baris ini adalah Finally yang dieksekusi");
        }
        System.out.println("Akhir Program");
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
                            product_Adapter = new DokterAdapter(dokter_List, News.this);
                            recyclerView.setAdapter(product_Adapter);
                            product_Adapter.notifyDataSetChanged();
                        mShimmerViewContainer.stopShimmerAnimation();
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

    public void profil_page(View view) {
        startActivity(new Intent(News.this, MyProfile.class));
    }

    public void goBalai(View view) {
        startActivity(new Intent(News.this, ProfilBalai.class));
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

    private void model_menu(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                News.this,R.style.bottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_bottom_sheet_munu, (RelativeLayout) findViewById(R.id.menu_menu)
                );

        bottomSheetView.findViewById(R.id.keluar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                finish();
            }
        });//fungsi untuk keluar


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }


}
