package com.example.petscare.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jenis {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nama_hewan")
    @Expose
    private String jenis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
