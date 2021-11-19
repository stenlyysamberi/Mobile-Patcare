package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petscare.Class.Login;
import com.example.petscare.Class.MyProfil;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.example.petscare.Response.MyResponse;
import com.example.petscare.SessionManager.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity {
    SessionManager sessionManager;
    String id;

    private Bitmap bitmap;
    private static final int INTENT_REQUEST_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText nama_lengkap_profil_saya,alamat_rumah,phone_sya;
        CardView simpan_profil;

        setContentView(R.layout.activity_my_profile);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        id = user.get(SessionManager.kunci_id);
        nama_lengkap_profil_saya = findViewById(R.id.nama_lengkap_profil_saya);
        alamat_rumah = findViewById(R.id.alamat_rumah);
        phone_sya    = findViewById(R.id.phone_sya);

        simpan_profil = (CardView) findViewById(R.id.simpan_profil);
        simpan_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String nama = nama_lengkap_profil_saya.getText().toString();
            String alamat = alamat_rumah.getText().toString();
            String phone   = phone_sya.getText().toString();

            if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(alamat) || TextUtils.isEmpty(phone)){
                Toast.makeText(getApplicationContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
            }else{

                update_akun(nama,alamat,phone);
            }

            }
        });

        getProfile();
    }

    private void update_akun(final String nama, String alamat, String phone) {

        try {
            Interfaces i = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<MyResponse> cl = i.edit_profil(id,nama,alamat,phone);
            cl.enqueue(new Callback<MyResponse>() {
                @Override
                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                    Toast.makeText(getApplicationContext(), "Ready", Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful() && response.body()!=null){

                        if (response.body().getResult().equals("berhasil")){
                            Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal Connect" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                    Log.e("update-failed", String.valueOf(t));
                }
            });

        }catch (Exception e){
            Log.e("logDP", String.valueOf(e));
        }

    }

    private void getProfile(){
        try {
            Interfaces pr = RestClient.getRetrofitInstance().create(Interfaces.class);

            Call<MyProfil> mymodel = pr.fect_profil(id);
            mymodel.enqueue(new Callback<MyProfil>() {
                @Override
                public void onResponse(Call<MyProfil> call, Response<MyProfil> response) {

                    if (response.isSuccessful() && response.body()!=null){
                        EditText nm =  findViewById(R.id.nama_lengkap_profil_saya);
                        nm.setText(response.body().getNama());

//                        Toast.makeText(getApplicationContext(), "" + nm, Toast.LENGTH_SHORT).show();

                        EditText alamat =  findViewById(R.id.alamat_rumah);
                        alamat.setText(response.body().getAlamat());

                        EditText phone = findViewById(R.id.phone_sya);
                        phone.setText(response.body().getPhone());

//                        ImageView imageView = (ImageView) findViewById(R.id.image_profil_saya);

//                        Picasso.get()
//                                .load(response.body().getGambar())
//                               .placeholder(R.drawable.placeholder)
//                                .error(R.drawable.error)
//                                .fit()
//                                .into(imageView);

                    }else{
                        Log.e("body_error","BodyKosong");
                    }

                }

                @Override
                public void onFailure(Call<MyProfil> call, Throwable t) {
                    Log.e("body_failure", String.valueOf(t));
                }
            });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "error"+ e, Toast.LENGTH_SHORT).show();
        }

    }

    public void model_foto(View view) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                MyProfile.this,R.style.bottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_bottom_sheet_profil, (LinearLayout) findViewById(R.id.pilih_foto)
                );



        bottomSheetView.findViewById(R.id.pilih_foto_galeri).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, INTENT_REQUEST_CODE);
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }


}