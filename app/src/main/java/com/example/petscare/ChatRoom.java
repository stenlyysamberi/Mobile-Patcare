package com.example.petscare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.petscare.Adapter.BalaiAdapter;
import com.example.petscare.Adapter.PesanAdapter;
import com.example.petscare.Class.Pesan;
import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
import com.example.petscare.Response.MyResponse;
import com.example.petscare.SessionManager.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatRoom extends AppCompatActivity {

    String currenttime;
    Calendar calendar;
    ImageView dp_dokter_chat;
    SimpleDateFormat simpleDateFormat;

    SessionManager sessionManager;
    TextView nama_chatroom,phone_chat,kirim;
    EditText tv_pesan;
    String id;

    PesanAdapter pesanAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Pesan> pesanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);




        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        id = user.get(SessionManager.kunci_id);
        dp_dokter_chat = findViewById(R.id.dp_dokter_chat);


        recyclerView = findViewById(R.id.recy_chat_room);
        layoutManager    = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        kirim         = findViewById(R.id.kirim);
        tv_pesan      = (EditText) findViewById(R.id.tv_pesan);
        nama_chatroom = findViewById(R.id.nama_chatroom);
        phone_chat    = findViewById(R.id.phone_chat);
        String nama = getIntent().getStringExtra("nama");
        String phone = getIntent().getStringExtra("phone");
        String level = getIntent().getStringExtra("level");
        String image = getIntent().getStringExtra("profil");
        String receiveruid = getIntent().getStringExtra("id");
        phone_chat.setText(phone);
        nama_chatroom.setText(nama);

        Glide.with(this)
                .load("http://192.168.42.246:8000/storage/" + image)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(dp_dokter_chat);

        TextView textView = findViewById(R.id.title_id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatRoom.this, News.class));
                finish();
            }
        });

        get_message(id, receiveruid);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pesan  = tv_pesan.getText().toString();
                if (TextUtils.isEmpty(pesan)){
                    Toast.makeText(getApplicationContext(), "Enter message." + pesan, Toast.LENGTH_SHORT).show();
                }else{
                    send_message(pesan,receiveruid);

                }

            }
        });
    }

    private void get_message(String senderId, String receiveId) {

        try {
            Interfaces interfaces = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<List<Pesan>> pesan = interfaces.get_pesan(senderId,receiveId);
            pesan.enqueue(new Callback<List<Pesan>>() {
                @Override
                public void onResponse(Call<List<Pesan>> call, Response<List<Pesan>> response) {
                    if (response.isSuccessful() && response.body() != null){
                        if(response.code() == 200){
                            pesanList = response.body();
                            pesanAdapter = new PesanAdapter(pesanList, ChatRoom.this);
                            recyclerView.setAdapter(pesanAdapter);
                            pesanAdapter.notifyDataSetChanged();
                        }



                    }

                }

                @Override
                public void onFailure(Call<List<Pesan>> call, Throwable t) {

                }
            });

        }catch (Exception e){
            Log.e("get_pesan", String.valueOf(e));
        }

    }

    private void send_message(String text_pesan, String receiveruid ){
        try {
        //D/ate date = new Date();
        ///currenttime=simpleDateFormat.format(calendar.getTime());
        String currenttime = DateFormat.getDateTimeInstance().format(new Date());



            Interfaces interfaces = RestClient.getRetrofitInstance().create(Interfaces.class);
            Call<MyResponse> call = interfaces.kirim_pesan(id,text_pesan,currenttime,receiveruid);
            call.enqueue(new Callback<MyResponse>() {
                @Override
                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                    if (response.isSuccessful() && response.body()!=null){

                        if (response.body().getResult().equals("massage sended.")){
                            Toast.makeText(getApplicationContext(), "massage sended.", Toast.LENGTH_SHORT).show();
                            tv_pesan.setText("");

                            get_message(id, receiveruid);


                        }else{
                            Toast.makeText(getApplicationContext(), "massage failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal Connect" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                    Log.e("chat", String.valueOf(t));
                }
            });



        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "eror" + e, Toast.LENGTH_SHORT).show();
        }


    }
}