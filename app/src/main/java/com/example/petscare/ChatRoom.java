package com.example.petscare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class ChatRoom extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;
    TextView nama_chatroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        auth = FirebaseAuth.getInstance();

        nama_chatroom = findViewById(R.id.nama_chatroom);

        String nama = getIntent().getStringExtra("nama");
        String phone = getIntent().getStringExtra("phone");
        String level = getIntent().getStringExtra("level");

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(level)){
            Toast.makeText(getApplicationContext(), "all fileds are required.", Toast.LENGTH_SHORT).show();
        }else{
            nama_chatroom.setText(nama);
            register(nama,phone,level);
        }


    }

    private void register(String username, String phone, String level){

        try {

            auth.createUserWithEmailAndPassword(phone,level)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                String userid = firebaseUser.getUid();
                                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("username", username);
                                hashMap.put("imageUrl", "default");


                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Firebase has been connected!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Failed Connected", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Failed Connected.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "eror" + e, Toast.LENGTH_SHORT).show();
        }


    }
}