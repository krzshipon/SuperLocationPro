package com.bjit.shipon.superlocationpro.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TimeZoneList {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("zones")
    private List<Timezone> zones;

    public TimeZoneList(String status, String message, List<Timezone> zones) {
        this.status = status;
        this.message = message;
        this.zones = zones;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Timezone> getZones() {
        return zones;
    }

    public void setZones(List<Timezone> zones) {
        this.zones = zones;
    }

    @Override
    public String toString() {
        return "TimeZoneList{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", zones=" + zones +
                '}';
    }
}


