package com.example.petscare.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProfil {

    @SerializedName("id")
    @Expose
    private  String id;

    @SerializedName("name")
    @Expose
    private  String nama;

    @SerializedName("alamat")
    @Expose
    private  String alamat;

    @SerializedName("phone")
    @Expose
    private  String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
