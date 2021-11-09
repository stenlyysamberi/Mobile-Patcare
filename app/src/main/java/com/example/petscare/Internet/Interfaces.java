package com.example.petscare.Internet;

import com.example.petscare.Class.Artikel;
import com.example.petscare.Class.Balai;
import com.example.petscare.Class.Dokter;
import com.example.petscare.Class.Login;
import com.example.petscare.Class.MyProfil;
import com.google.gson.annotations.Expose;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Interfaces {

    @GET("penyakit")
    Call<List<Artikel>> getArtikel();

    @GET("dokter")
    Call<List<Dokter>> getDok();

    @GET("balai")
    Call<List<Balai>> getBalai();

    @FormUrlEncoded
    @POST("login")
    Call<Login> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("get_profil")
    Call<MyProfil> fect_profil(
            @Field("id") String id

    );






}
