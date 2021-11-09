package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petscare.Class.Artikel;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.example.petscare.SessionManager.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    SessionManager sessionManager;
    ProgressBar progressBar;
    TextView result_koneksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        result_koneksi = findViewById(R.id.pesanKoneksi);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        changeStatusBarColor();
        getKoneksi();

    }



    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void getKoneksi() {
        try {

            Interfaces apiInterface = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Artikel>> call = apiInterface.getArtikel();
            call.enqueue(new Callback<List<Artikel>>() {
                @Override
                public void onResponse(Call<List<Artikel>> call, Response<List<Artikel>> response) {

                    Artikel value = new Artikel();

                    if (response.isSuccessful() && response.body() !=null){
                        sessionManager = new SessionManager(getApplicationContext());
                        if (!sessionManager.is_login()){
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(SplashScreen.this, News.class);
                            startActivity(intent);
                            finish();
                        }


                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        result_koneksi.setVisibility(View.VISIBLE);
                        //Toast.makeText(getApplicationContext(), "Koneksi Anda terputus, Silakan coba lagi" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Artikel>> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    result_koneksi.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "Koneksi Anda terputus, Silakan coba lagi" , Toast.LENGTH_SHORT).show();
                    Log.e("Kesalahan", String.valueOf(t.fillInStackTrace()));

                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Baris ini adalam finnaly yang dieksekusi");
        }
        System.out.println("Akhir Program");
    }
}