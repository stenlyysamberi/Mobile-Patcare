package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petscare.Adapter.AdapterMyPats;
import com.example.petscare.Adapter.ArtikelAdapter;
import com.example.petscare.Class.MyPet;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.example.petscare.Response.MyResponse;
import com.example.petscare.SessionManager.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPat extends AppCompatActivity {
    SwipeRefreshLayout mypats_refresh;
    Button add_my_pats;
    SessionManager sessionManager;
    String user_id;
    RecyclerView recyclerView;
    AdapterMyPats adapterMyPats;
    List<MyPet> myPets;
    TextView textView;
    RecyclerView.LayoutManager LayoutManager;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pat);

        mypats_refresh = (SwipeRefreshLayout) findViewById(R.id.mypats_refresh);
        // Mengeset properti warna yang berputar pada SwipeRefreshLayout
        mypats_refresh.setColorSchemeResources(R.color.teal_200,R.color.teal_700);

        recyclerView = (RecyclerView) findViewById(R.id.recy_myPats);
        textView = (TextView) findViewById(R.id.ket_pat);

        LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setHasFixedSize(true);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.kunci_id);

        back = findViewById(R.id.title_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPat.this, News.class));
                finish();
            }
        });


        add_my_pats = findViewById(R.id.add_my_pats);
        add_my_pats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_add_my_pats();
            }
        });

        mypats_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mypats_refresh.setRefreshing(false);
                        load_data_server();
                    }
                },5000);

            }
        });

        load_data_server();
    }

    private void load_data_server() {
        try {

            Interfaces interfaces = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<MyPet>> call = interfaces.get_myPat(user_id,"2");

            call.enqueue(new Callback<List<MyPet>>() {
                @Override
                public void onResponse(Call<List<MyPet>> call, Response<List<MyPet>> response) {
                    if (response.isSuccessful() && response.body()!=null){

                        myPets = response.body();
                        adapterMyPats = new AdapterMyPats(myPets, MyPat.this);
                        recyclerView.setAdapter(adapterMyPats);
                        adapterMyPats.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.INVISIBLE);

//                        if (response.body().get(0).getResult().equals("")){
//                            Toast.makeText(getApplicationContext(), ""+response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
//
//
//                        }else{
//                            Toast.makeText(getApplicationContext(), ""+response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
//                            textView.setVisibility(View.INVISIBLE);
//                        }

                    }else{
                        Log.e("mypet", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<List<MyPet>> call, Throwable t) {
                    Log.e("mypets", String.valueOf(t));
                }
            });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void dialog_add_my_pats() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                MyPat.this,R.style.bottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_button_sheet_add_my_pats, (RelativeLayout) findViewById(R.id.bottomSheetAddPats)
                );

        bottomSheetView.findViewById(R.id.bnt_add_pats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nama = bottomSheetView.findViewById(R.id.nama_hewan);
                String namas = nama.getText().toString();

                EditText jenis = bottomSheetView.findViewById(R.id.jenis_hewan);
                String jenis_h = jenis.getText().toString();

                EditText tgl = bottomSheetView.findViewById(R.id.tlg_lahir);
                String tgl_l = tgl.getText().toString();

                EditText umur = bottomSheetView.findViewById(R.id.umur);
                String umurs = umur.getText().toString();

                try {

                    if (namas.isEmpty() || jenis_h.isEmpty() || tgl_l.isEmpty() || umurs.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
                    }else{

                        Interfaces y = RestClient.getRetrofitInstance().create(Interfaces.class);
                        Call<MyResponse> call = y.push_MyPets(user_id,jenis_h,namas,tgl_l,umurs);
                        call.enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                                if (response.body().getResult().equals("berhasil")){
                                    Toast.makeText(getApplicationContext(), "has been added", Toast.LENGTH_SHORT).show();
                                    bottomSheetDialog.dismiss();
                                }else{
                                    Toast.makeText(getApplicationContext(), "has been valied", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "requst error" + t, Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                }catch (Exception e){
                    Log.e("error", "gagal");
                }
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }
}