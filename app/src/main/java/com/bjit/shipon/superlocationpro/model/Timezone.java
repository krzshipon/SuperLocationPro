package com.bjit.shipon.superlocationpro.model;

import com.google.gson.annotations.SerializedName;


public class Timezone implements Comparable<Timezone> {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("countryCode")
    private String countryCode;

    @SerializedName("countryName")
    private String countryName;

    @SerializedName("zoneName")
    private String zoneName;

    @SerializedName("abbreviation")
    private String abbreviation;

    @SerializedName("gmtOffset")
    private long gmtOffset;

    @SerializedName("dst")
    private String dst;

    @SerializedName("zoneStart")
    private String zoneStart;

    @SerializedName("zoneEnd")
    private String zoneEnd;


    @SerializedName("nextAbbreviation")
    private String nextAbbreviation;


    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("formatted")
    private String formatted;

    public Timezone(String status, String message, String countryCode, String countryName, String zoneName, String abbreviation, long gmtOffset, String dst, String zoneStart, String zoneEnd, String nextAbbreviation, long timestamp, String formatted) {
        this.status = status;
        this.message = message;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.zoneName = zoneName;
        this.abbreviation = abbreviation;
        this.gmtOffset = gmtOffset;
        this.dst = dst;
        this.zoneStart = zoneStart;
        this.zoneEnd = zoneEnd;
        this.nextAbbreviation = nextAbbreviation;
        this.timestamp = timestamp;
        this.formatted = formatted;

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public long getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(long gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getZoneStart() {
        return zoneStart;
    }

    public void setZoneStart(String zoneStart) {
        this.zoneStart = zoneStart;
    }

    public String getZoneEnd() {
        return zoneEnd;
    }

    public void setZoneEnd(String zoneEnd) {
        this.zoneEnd = zoneEnd;
    }

    public String getNextAbbreviation() {
        return nextAbbreviation;
    }

    public void setNextAbbreviation(String nextAbbreviation) {
        this.nextAbbreviation = nextAbbreviation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    @Override
    public String toString() {
        return "Timezone{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", zoneName='" + zoneName + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", gmtOffset=" + gmtOffset +
                ", dst='" + dst + '\'' +
                ", zoneStart='" + zoneStart + '\'' +
                ", zoneEnd='" + zoneEnd + '\'' +
                ", nextAbbreviation='" + nextAbbreviation + '\'' +
                ", timestamp=" + timestamp +
                ", formatted='" + formatted + '\'' +
                '}';
    }

    @Override
    public int compareTo(Timezone o) {
        return Integer.valueOf((int) this.getGmtOffset()).compareTo(Integer.valueOf((int) o.getGmtOffset()));
    }



}
