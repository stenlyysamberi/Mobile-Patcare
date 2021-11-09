package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petscare.Class.Login;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.example.petscare.SessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    SessionManager sessionManager;
    ProgressBar progressBar;
    EditText username, password;
    TextView register,textLogin;
    CardView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        sessionManager = new SessionManager(getApplicationContext());
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.sing_up);
        button   = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progres);
        textLogin = findViewById(R.id.text_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }

            
        });
    }

    private void login(){

        String user = username.getText().toString();
        String sandi = password.getText().toString();

        try {

            if (user.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_SHORT).show();
            }else if( sandi.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            }else{
                progressBar.setVisibility(View.VISIBLE);
                textLogin.setVisibility(View.INVISIBLE);

                Interfaces x = RestClient.getRetrofitInstance().create(Interfaces.class);
                Call<Login> call = x.login(user,sandi);

                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful() && response.body()!=null){

                            String id = response.body().getId();
                            String name = response.body().getNama();

                            //Toast.makeText(getApplicationContext(), "" + id + name, Toast.LENGTH_SHORT).show();

                            if (response.body().getResult().equals("berhasil")){
                                progressBar.setVisibility(View.INVISIBLE);
                                textLogin.setVisibility(View.VISIBLE);
                                sessionManager.create_session(id,name);
                                Intent intent = new Intent(LoginPage.this, News.class);
                                //Toast.makeText(getApplicationContext(), "Login Succesfully", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                textLogin.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Failed username or password, Please Try again.", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        textLogin.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginPage.this, "Gagal Login Error :" + t, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }catch (Exception e){
            progressBar.setVisibility(View.INVISIBLE);
            textLogin.setVisibility(View.VISIBLE);
            Log.e("error","Gagal!"+e);
        }

    }


}