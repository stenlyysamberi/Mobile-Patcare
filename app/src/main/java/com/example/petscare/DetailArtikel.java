package com.example.petscare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailArtikel extends AppCompatActivity {
    RelativeLayout relativeLayout;
    String id,title,jenis,image,create,update,isi_content;
    TextView  detil_jenis,detil_judul,detil_isi,create_at;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);
        Bundle extras = getIntent().getExtras();

        TextView textView = findViewById(R.id.title_id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailArtikel.this, News.class));
                finish();
            }
        });

        id = extras.getString("id");
        title = extras.getString("title");
        jenis = extras.getString("jenis");
        isi_content =extras.getString("isi");
        image = extras.getString("image");
        create = extras.getString("created_at");
        update = extras.getString("updated_id");

        //Toast.makeText(getApplicationContext(), "" + title, Toast.LENGTH_SHORT).show();

        relativeLayout = (RelativeLayout) findViewById(R.id.lyt_detail);
        TextView tx = (TextView) findViewById(R.id.title_id);
        detil_judul = (TextView) findViewById(R.id.detil_judul);
        detil_jenis = (TextView) findViewById(R.id.detil_jenis);
        detil_isi   = (TextView) findViewById(R.id.detil_isi);
        create_at   = (TextView) findViewById(R.id.create_at);
        imageView   = (ImageView) findViewById(R.id.img_detail);

        detil_judul.setText(title);

       

        if (jenis == null){
            detil_jenis.setText("Profil");
        }else{
            detil_jenis.setText(jenis);
        }

        create_at.setText(create);
        detil_isi.setText(Html.fromHtml(isi_content));

        Glide.with(this)
                .load("http://192.168.42.246:8000/storage/" + image)
                .into(imageView);



    }
}