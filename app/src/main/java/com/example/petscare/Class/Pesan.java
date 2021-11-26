package com.example.petscare.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pesan {

    @SerializedName("message")
    @Expose
    private  String message;

    @SerializedName("senderId")
    @Expose
    private  String senderId;

    @SerializedName("receiveruid")
    @Expose
    private  String receiveruid;

    public String getReceiveruid() {
        return receiveruid;
    }

    public void setReceiveruid(String receiveruid) {
        this.receiveruid = receiveruid;
    }

    @SerializedName("timestamp")
    @Expose
    private  String timestamp;

    @SerializedName("currenttime")
    @Expose
    private  String currenttime;


    public Pesan(){

    }

    public Pesan(String message, String senderId, String timestamp, String currenttime) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.currenttime = currenttime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
}
