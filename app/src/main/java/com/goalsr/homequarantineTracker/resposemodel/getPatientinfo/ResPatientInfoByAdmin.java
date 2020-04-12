package com.goalsr.homequarantineTracker.resposemodel.getPatientinfo;

public class ResPatientInfoByAdmin {

    private int CitizenID;

    private String Name;
    private int GenderId;
    private String MobileNo;
    private String Email;
    private String DateOfArrial;
    private String DateOfQurantine;
    private String PlaceOfOrigin;
    private String PlaceOfArrival;
    private String HouseNo;
    private String Building;
    private String Street;
    private String City;
    private String CreatedDate;//2020-03-27T12:24:06.35
    private String AdditionalInfo;
    private double latitude=0.0;
    private double longitude=0.0;
    private int distCode=-1;
    private int TalukCode=-1;
    private boolean Citz_CoughSourThroat;
    private boolean Citz_Fever;
    private boolean Citz_BreathingProb;
    private boolean Citz_Diarrhea;

    private int URoleID=-1;
    private int Age=0;
    private boolean Diabetes;
    private boolean Hypertension;
    private boolean HeartIssue;
    private boolean HIV;

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

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
        GenderId = genderId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDateOfArrial() {
        return DateOfArrial;
    }

    public void setDateOfArrial(String dateOfArrial) {
        DateOfArrial = dateOfArrial;
    }

    public String getDateOfQurantine() {
        return DateOfQurantine;
    }

    public void setDateOfQurantine(String dateOfQurantine) {
        DateOfQurantine = dateOfQurantine;
    }

    public String getPlaceOfOrigin() {
        return PlaceOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        PlaceOfOrigin = placeOfOrigin;
    }

    public String getPlaceOfArrival() {
        return PlaceOfArrival;
    }

    public void setPlaceOfArrival(String placeOfArrival) {
        PlaceOfArrival = placeOfArrival;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
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

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
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

    public int getDistCode() {
        return distCode;
    }

    public void setDistCode(int distCode) {
        this.distCode = distCode;
    }

    public int getTalukCode() {
        return TalukCode;
    }

    public void setTalukCode(int talukCode) {
        TalukCode = talukCode;
    }

    public boolean isCitz_CoughSourThroat() {
        return Citz_CoughSourThroat;
    }

    public void setCitz_CoughSourThroat(boolean citz_CoughSourThroat) {
        Citz_CoughSourThroat = citz_CoughSourThroat;
    }

    public boolean isCitz_Fever() {
        return Citz_Fever;
    }

    public void setCitz_Fever(boolean citz_Fever) {
        Citz_Fever = citz_Fever;
    }

    public boolean isCitz_BreathingProb() {
        return Citz_BreathingProb;
    }

    public void setCitz_BreathingProb(boolean citz_BreathingProb) {
        Citz_BreathingProb = citz_BreathingProb;
    }

    public boolean isCitz_Diarrhea() {
        return Citz_Diarrhea;
    }

    public void setCitz_Diarrhea(boolean citz_Diarrhea) {
        Citz_Diarrhea = citz_Diarrhea;
    }

    public int getURoleID() {
        return URoleID;
    }

    public void setURoleID(int URoleID) {
        this.URoleID = URoleID;
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
}
