package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.example.petscare.Response.MyResponse;
import com.example.petscare.SessionManager.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPat extends AppCompatActivity {

    Button add_my_pats;
    SessionManager sessionManager;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pat);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.kunci_id);


        add_my_pats = findViewById(R.id.add_my_pats);
        add_my_pats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_add_my_pats();
            }
        });

        load_data_server();
    }

    private void load_data_server() {
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

                Spinner jenis = bottomSheetView.findViewById(R.id.jenis_hewan);
                String jenis_h = (String) jenis.getSelectedItem();

                EditText tgl = bottomSheetView.findViewById(R.id.tlg_lahir);
                String tgl_l = tgl.getText().toString();

                EditText umur = bottomSheetView.findViewById(R.id.umur);
                String umurs = umur.getText().toString();

                try {

                    if (namas.isEmpty() || jenis_h.isEmpty() || tgl_l.isEmpty() || umurs.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
                    }else{

                        Interfaces y = RestClient.getRetrofitInstance().create(Interfaces.class);
                        Call<MyResponse> call = y.push_MyPets(user_id,"1",namas,tgl_l,umurs);
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