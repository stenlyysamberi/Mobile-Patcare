package com.example.petscare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.petscare.Internet.Interfaces;
import com.example.petscare.Internet.RestClient;
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

import java.util.HashMap;


public class ChatRoom extends AppCompatActivity {

    TextView nama_chatroom,phone_chat,kirim;
    EditText tv_pesan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        kirim         = findViewById(R.id.kirim);
        tv_pesan      = findViewById(R.id.tv_pesan);
        nama_chatroom = findViewById(R.id.nama_chatroom);
        phone_chat    = findViewById(R.id.phone_chat);
        String nama = getIntent().getStringExtra("nama");
        String phone = getIntent().getStringExtra("phone");
        String level = getIntent().getStringExtra("level");
        String pesan = String.valueOf(tv_pesan.getText());
        phone_chat.setText(phone);
        nama_chatroom.setText(nama);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(pesan)){
                    Toast.makeText(getApplicationContext(), "Enter message.", Toast.LENGTH_SHORT).show();
                }else{
                    send_message(pesan);
                }

            }
        });




    }

    private void send_message( String text_pesan){

        try {

            Interfaces interfaces = RestClient.getRetrofitInstance().create(Interfaces.class);




        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "eror" + e, Toast.LENGTH_SHORT).show();
        }


    }
}