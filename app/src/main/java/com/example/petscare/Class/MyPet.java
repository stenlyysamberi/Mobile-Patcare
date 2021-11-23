package com.example.petscare.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPet {

    @SerializedName("result")
    @Expose
    private  String result;

    @SerializedName("nama_hewan")
    @Expose
    private  String nama;

    @SerializedName("tgl_lahir")
    @Expose
    private  String ttl;

    @SerializedName("umur")
    @Expose
    private  String umur;

    @SerializedName("id_jenis_hewans")
    @Expose
    private  String jenis;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
