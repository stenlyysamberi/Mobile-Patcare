package com.example.petscare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petscare.Adapter.ArtikelAdapter;
import com.example.petscare.Adapter.GejalaAdapter;

import com.example.petscare.Adapter.JenisAdapter;
import com.example.petscare.Class.Artikel;
import com.example.petscare.Class.Dokter;
import com.example.petscare.Class.Gejala;
import com.example.petscare.Class.Jenis;
import com.example.petscare.Class.RecySelect;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medical extends AppCompatActivity {
    TextView tv_empty,tv_select_gejala,tv_select_jenis;
//    boolean[] selectDay;
//    ArrayList<Integer> dayList = new ArrayList<>();
//    String[] dayArray = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
    //List<Gejala> dayArray;

    RecyclerView recyclerView;
    GejalaAdapter gejalaAdapter;
    StaggeredGridLayoutManager layoutManager;
    List<Gejala> list;

    JenisAdapter jenisAdapter;
    StaggeredGridLayoutManager layoutManager_jenis;
    RecyclerView recyclerView_jenis;
    List<Jenis> jenis;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

       
        tv_select_gejala = findViewById(R.id.tv_select_gejala);
        tv_select_jenis  = findViewById(R.id.tv_select_jenis);


        tv_select_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model_sheet_jenis();
            }
        });

        tv_select_gejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  model_sheet_gejala();

            }
        });

        back = findViewById(R.id.title_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Medical.this, News.class));
                finish();
            }
        });

    }


    private void model_sheet_jenis() {



        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                Medical.this,R.style.bottomSheetDialogTheme);

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_bottom_sheet_gejala, (LinearLayout) findViewById(R.id.pilih_gejala));

        try {



            TextView tv_header = bottomSheetView.findViewById(R.id.text_header);
            tv_header.setText("Jenis Peliharaan");
            ProgressBar progressBar = bottomSheetView.findViewById(R.id.proges_gejala);
            recyclerView_jenis     = bottomSheetView.findViewById(R.id.recy_bottomSheet_gejala);
            recyclerView_jenis.addItemDecoration(new RecySelect(this));
            layoutManager_jenis    = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
            recyclerView_jenis.setLayoutManager(layoutManager_jenis);
            recyclerView_jenis.setHasFixedSize(true);

            Interfaces i = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Jenis>> getAll = i.getJenis();
            getAll.enqueue(new Callback<List<Jenis>>() {
                @Override
                public void onResponse(Call<List<Jenis>> call, Response<List<Jenis>> response) {
                    List<Jenis> values = response.body();
                    //Toast.makeText(getApplicationContext(), "" + values.get(0).getJenis(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful() && response.body() != null){
                        jenis = response.body();
                        jenisAdapter = new JenisAdapter(jenis, Medical.this);
                        recyclerView_jenis.setAdapter(jenisAdapter);
                        jenisAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<List<Jenis>> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Connection Server Failed :" + t, Toast.LENGTH_SHORT);
                }
            });

        }catch (Exception e){
            Log.e("caseGejala", String.valueOf(e));
        }


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }


    private void model_sheet_gejala() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                Medical.this,R.style.bottomSheetDialogTheme);

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_bottom_sheet_gejala, (LinearLayout) findViewById(R.id.pilih_gejala));

        try {
            ProgressBar progressBar = bottomSheetView.findViewById(R.id.proges_gejala);
            recyclerView     = bottomSheetView.findViewById(R.id.recy_bottomSheet_gejala);
            recyclerView.addItemDecoration(new RecySelect(this));
            layoutManager    = new StaggeredGridLayoutManager(5, LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            Interfaces i = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Gejala>> getAll = i.getGejala();
            getAll.enqueue(new Callback<List<Gejala>>() {
                @Override
                public void onResponse(Call<List<Gejala>> call, Response<List<Gejala>> response) {

                    List<Gejala> values = response.body();
                    //Toast.makeText(getApplicationContext(), "" + values.get(0).getNama_gejala(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful() && response.body() != null){
                        list = response.body();
                        gejalaAdapter = new GejalaAdapter(list, Medical.this);
                        recyclerView.setAdapter(gejalaAdapter);
                        gejalaAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                }

                @Override
                public void onFailure(Call<List<Gejala>> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Connection Server Failed :" + t, Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.e("caseGejala", String.valueOf(e));
        }


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }




}