package com.example.petscare.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balai {
    @SerializedName("id")
    @Expose
    private  String id;

    @SerializedName("judul")
    @Expose
    private  String judul;

    @SerializedName("img_p")
    @Expose
    private  String img_p;

    @SerializedName("isi")
    @Expose
    private  String isi;

    @SerializedName("created_at")
    @Expose
    private  String created_at;

    @SerializedName("updated_at")
    @Expose
    private  String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getImg_p() {
        return img_p;
    }

    public void setImg_p(String img_p) {
        this.img_p = img_p;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
