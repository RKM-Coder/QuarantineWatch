package com.goalsr.homequarantineTracker.resposemodel.getPatientinfo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity (tableName = "patient_info")
public class ResPatientInfo {


    @PrimaryKey
    @NonNull
    private int CitizenID;

    private String Name;
    private int GenID;
    private String Mobile;
    private String Email;
    private String DOA;
    private String DateQurantine;
    private String POOrigin;
    private String PoArrival;
    private String HNo;
    private String Building;
    private String Street;
    private String City;
    private String CreatedDate;//2020-03-27T12:24:06.35
    private String Additional;
    private double latitude=0.0;
    private double longitude=0.0;
    private int districtCode=-1;
    private int talukCode=-1;
    private boolean CoughSourThroat;
    private boolean fever;
    private boolean BreathingProblem;
    private boolean Diarrhea;

    private int uRoleBy=-1;
    private int Age=0;
    private boolean Diabetes;
    private boolean Hypertension;
    private boolean HeartIssue;
    private boolean HIV;
    private int Cby;


    public int getCitizenID() {
        return CitizenID;
    }

    public void setCitizenID(int citizenID) {
        CitizenID = citizenID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getGenID() {
        return GenID;
    }

    public void setGenID(int genID) {
        GenID = genID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDOA() {
        return DOA;
    }

    public void setDOA(String DOA) {
        this.DOA = DOA;
    }

    public String getDateQurantine() {
        return DateQurantine;
    }

    public void setDateQurantine(String dateQurantine) {
        DateQurantine = dateQurantine;
    }

    public String getPOOrigin() {
        return POOrigin;
    }

    public void setPOOrigin(String POOrigin) {
        this.POOrigin = POOrigin;
    }

    public String getPoArrival() {
        return PoArrival;
    }

    public void setPoArrival(String poArrival) {
        PoArrival = poArrival;
    }

    public String getHNo() {
        return HNo;
    }

    public void setHNo(String HNo) {
        this.HNo = HNo;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getAdditional() {
        return Additional;
    }

    public void setAdditional(String additional) {
        Additional = additional;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(int districtCode) {
        this.districtCode = districtCode;
    }

    public int getTalukCode() {
        return talukCode;
    }

    public void setTalukCode(int talukCode) {
        this.talukCode = talukCode;
    }

    public boolean isCoughSourThroat() {
        return CoughSourThroat;
    }

    public void setCoughSourThroat(boolean coughSourThroat) {
        CoughSourThroat = coughSourThroat;
    }

    public boolean isFever() {
        return fever;
    }

    public void setFever(boolean fever) {
        this.fever = fever;
    }

    public boolean isBreathingProblem() {
        return BreathingProblem;
    }

    public void setBreathingProblem(boolean breathingProblem) {
        BreathingProblem = breathingProblem;
    }

    public boolean isDiarrhea() {
        return Diarrhea;
    }

    public void setDiarrhea(boolean diarrhea) {
        Diarrhea = diarrhea;
    }

    public int getURoleBy() {
        return uRoleBy;
    }

    public void setURoleBy(int uRoleBy) {
        this.uRoleBy = uRoleBy;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public boolean isDiabetes() {
        return Diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        Diabetes = diabetes;
    }

    public boolean isHypertension() {
        return Hypertension;
    }

    public void setHypertension(boolean hypertension) {
        Hypertension = hypertension;
    }

    public boolean isHeartIssue() {
        return HeartIssue;
    }

    public void setHeartIssue(boolean heartIssue) {
        HeartIssue = heartIssue;
    }

    public boolean isHIV() {
        return HIV;
    }

    public void setHIV(boolean HIV) {
        this.HIV = HIV;
    }

    public int getCby() {
        return Cby;
    }

    public void setCby(int cby) {
        Cby = cby;
    }
}
