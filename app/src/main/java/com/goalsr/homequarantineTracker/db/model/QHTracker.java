package com.goalsr.homequarantineTracker.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "qh_travel_tracking")
public class QHTracker {


    @NonNull
    @PrimaryKey
    private String primary_id;//userId_currentTimesec
    private String location_lat;
    private String location_lng;
    private String mcurrenttime;
    private String mcurrentdatetime;
    private String mcurrentdatetimejulian;

    private String typeofdatatracker;// it is tracking or status update
    private boolean syncstutas;
    private boolean syncstatusimage;
    private String selfifilepathlocal;
    private String selfifilepathserver;
    private String infodata;

    private String isAvlHomeChecker="0";

    public String getIsAvlHomeChecker() {
        return isAvlHomeChecker;
    }

    public void setIsAvlHomeChecker(String isAvlHomeChecker) {
        this.isAvlHomeChecker = isAvlHomeChecker;
    }

    public String getMcurrentdatetimejulian() {
        return mcurrentdatetimejulian;
    }

    public void setMcurrentdatetimejulian(String mcurrentdatetimejulian) {
        this.mcurrentdatetimejulian = mcurrentdatetimejulian;
    }

    @NonNull
    public String getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(@NonNull String primary_id) {
        this.primary_id = primary_id;
    }

    public String getMcurrentdatetime() {
        return mcurrentdatetime;
    }

    public void setMcurrentdatetime(String mcurrentdatetime) {
        this.mcurrentdatetime = mcurrentdatetime;
    }

    public String getTypeofdatatracker() {
        return typeofdatatracker;
    }

    public void setTypeofdatatracker(String typeofdatatracker) {
        this.typeofdatatracker = typeofdatatracker;
    }

    public boolean isSyncstutas() {
        return syncstutas;
    }

    public void setSyncstutas(boolean syncstutas) {
        this.syncstutas = syncstutas;
    }

    public boolean isSyncstatusimage() {
        return syncstatusimage;
    }

    public void setSyncstatusimage(boolean syncstatusimage) {
        this.syncstatusimage = syncstatusimage;
    }

    public String getSelfifilepathlocal() {
        return selfifilepathlocal;
    }

    public void setSelfifilepathlocal(String selfifilepathlocal) {
        this.selfifilepathlocal = selfifilepathlocal;
    }

    public String getSelfifilepathserver() {
        return selfifilepathserver;
    }

    public void setSelfifilepathserver(String selfifilepathserver) {
        this.selfifilepathserver = selfifilepathserver;
    }

    public String getInfodata() {
        return infodata;
    }

    public void setInfodata(String infodata) {
        this.infodata = infodata;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(String location_lng) {
        this.location_lng = location_lng;
    }

    public String getMcurrenttime() {
        return mcurrenttime;
    }

    public void setMcurrenttime(String mcurrenttime) {
        this.mcurrenttime = mcurrenttime;
    }
}
