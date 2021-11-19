package com.example.petscare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.petscare.Class.Artikel;
import com.example.petscare.Class.Dokter;
import com.example.petscare.Class.Gejala;
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
    TextView tv_empty,tv_select_gejala;
//    boolean[] selectDay;
//    ArrayList<Integer> dayList = new ArrayList<>();
//    String[] dayArray = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
    //List<Gejala> dayArray;

    RecyclerView recyclerView;
    GejalaAdapter gejalaAdapter;
    StaggeredGridLayoutManager layoutManager;

    Context mcontex;
    List<Gejala> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

       
        tv_select_gejala = findViewById(R.id.tv_select_gejala);




//        selectDay = new boolean[dayArray.length];
        tv_select_gejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                get_gejala();
                  model_sheet_gejala();
                //test();

//                AlertDialog.Builder builder = new AlertDialog.Builder(
//                        Medical.this
//                );
//                //Sett Title
//                builder.setTitle("Select Gejala");
//                //SetCancel
//                builder.setCancelable(false);
//                builder.setMultiChoiceItems(dayArray, selectDay, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                        if (b){
//                            Toast.makeText(getApplicationContext(), "Max Select 3", Toast.LENGTH_SHORT).show();
//                            //ketika nilai dipilih
//                            //tambahkan posisi ke array
//                            dayList.add(i);
//                            //tampikan urutan pada list
//                            Collections.sort(dayList);
//                        }else{
//                            //ketika unceklist data
//                            //hapus posisi dari arraylist
//                            dayList.remove(i);
//                        }
//                    }
//                });

//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //pemasangan string builder
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (int j= 0; j<dayList.size(); j++){
//                            stringBuilder.append(dayArray[dayList.get(j)]);
//                            //pemeriksaan kondisi
//                            if (j !=dayList.size()-1){
//                                //ketika nilai lebih dari 1
//                                //tambakan koma
//                                stringBuilder.append(", ");
//                            }
//                        }
//
//                        tv_select_gejala.setText(stringBuilder.toString());
//                    }
//                });

//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //tutup dialog
//                        dialogInterface.dismiss();
//                    }
//                });

//                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //looping nilai
//                        for (int j=0; j<selectDay.length; j++){
//                            //hapus semua seleksi
//                            selectDay[j] = false;
//                            //bersikan semua dari dalam list
//                            dayList.clear();
//                            //hapus nilai dari tetxview
//                            tv_select_gejala.setText("");
//                        }
//                    }
//                });
                //tampilan form dialog
//                builder.show();
            }
        });

    }

    private void test() {
        try {

            Interfaces i = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Gejala>> getAll = i.getGejala();
            getAll.enqueue(new Callback<List<Gejala>>() {
                @Override
                public void onResponse(Call<List<Gejala>> call, Response<List<Gejala>> response) {

                    List<Gejala> values = response.body();
                    Toast.makeText(getApplicationContext(), "" + values.get(0).getNama_gejala(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful() && response.body() != null){
                        list = response.body();
                        gejalaAdapter = new GejalaAdapter(list, Medical.this);
                        recyclerView.setAdapter(gejalaAdapter);
                        gejalaAdapter.notifyDataSetChanged();

                    }

                }

                @Override
                public void onFailure(Call<List<Gejala>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Connection Server Failed :" + t, Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.e("caseGejala", String.valueOf(e));
        }
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
            layoutManager    = new StaggeredGridLayoutManager(10, LinearLayoutManager.HORIZONTAL);
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

//    private void get_gejala(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(Medical.this);
//        //Sett Title
//        builder.setTitle("Select Gejala");
//        //SetCancel
//        builder.setCancelable(false);
//
//
//        try {
//            Interfaces gejala = RestClient.getRetrofitInstance().create(Interfaces.class);
//            Call<List<Gejala>> cal = gejala.getGejala();
//            cal.enqueue(new Callback<List<Gejala>>() {
//                @Override
//                public void onResponse(Call<List<Gejala>> call, Response<List<Gejala>> response) {
//                    List<Gejala> value =response.body();
//                    if (response.isSuccessful() && response.body() !=null){
//                        List<String> gejala_List = new ArrayList<>();
//                            for (int i = 0; i<value.size(); i++){
//                                gejala_List.add(value.get(i).getNama_gejala());
//                            }
//
//                           builder.setMultiChoiceItems(dayArray,selectDay, new DialogInterface.OnMultiChoiceClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//
//                                    if (b){
//                                        //Toast.makeText(getApplicationContext(), "Max Select 3", Toast.LENGTH_SHORT).show();
//                                        //ketika nilai dipilih
//                                        //tambahkan posisi ke array
//                                        //dayList.add(i);
//                                        //tampikan urutan pada list
//                                        //Collections.sort(dayList);
//                                    }else{
//                                        //ketika unceklist data
//                                        //hapus posisi dari arraylist
//                                        //dayList.remove(i);
//                                    }
//
//                                }
//                            });
//                        builder.show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Gejala>> call, Throwable t) {
//
//                }
//            });
//
//        }catch (Exception e){
//            Log.e("error", String.valueOf(e));
//        }
//    }


}