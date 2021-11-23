package com.example.petscare.Internet;

import com.example.petscare.Class.Artikel;
import com.example.petscare.Class.Balai;
import com.example.petscare.Class.Dokter;
import com.example.petscare.Class.Gejala;
import com.example.petscare.Class.Jenis;
import com.example.petscare.Class.Login;
import com.example.petscare.Class.MyPet;
import com.example.petscare.Class.MyProfil;
import com.example.petscare.Class.Pesan;
import com.example.petscare.Response.MyResponse;
import com.google.gson.annotations.Expose;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @POST("edit_profil/{id}")
    Call<MyResponse> edit_profil(
            @Path("id") String id,
            @Field("name") String name,
            @Field("alamat") String alamat,
            @Field("phone") String phone

    );


    @GET("gejala")
    Call<List<Gejala>>getGejala();

    @GET("jenis")
    Call<List<Jenis>>getJenis();


    @FormUrlEncoded
    @POST("addMyPats")
    Call<MyResponse>  push_MyPets(
            @Field("id_pemilik_hewans") String id_pemilik,
            @Field("id_jenis_hewans") String id_jenis,
            @Field("nama_hewan") String nama_hewa,
            @Field("tgl_lahir") String tgl,
            @Field("umur") String umur


    );

    @FormUrlEncoded
    @POST("pet/{id}")
    Call<List<MyPet>> get_myPat(
            @Path("id") String id,
            @Field("ids") String ids
    );

    @FormUrlEncoded
    @POST("message/{senderId}")
    Call<List<Pesan>> kirim_pesan(
            @Path("senderId") String senderId,
            @Field("message") String message,
            @Field("currenttime") String currenttime

    );









}
