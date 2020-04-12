package com.goalsr.homequarantineTracker.resposemodel.poststatus;

public class ReqSymtomBody {

    private String userId;
    private String localId;

    private String latitude;

    private String longitude;

    private String dateTime;

    private String typeOfTracker;

    private String syncStatus;

    private String syncStatusImage;

    private String selfie;
    private String selfiePathLocal;
    private String selfiePathServer;

    private String message;

    private String symptoms;

    private String locationTrack;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSelfiePathLocal() {
        return selfiePathLocal;
    }

    public void setSelfiePathLocal(String selfiePathLocal) {
        this.selfiePathLocal = selfiePathLocal;
    }

    public String getSelfiePathServer() {
        return selfiePathServer;
    }

    public void setSelfiePathServer(String selfiePathServer) {
        this.selfiePathServer = selfiePathServer;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTypeOfTracker() {
        return typeOfTracker;
    }

    public void setTypeOfTracker(String typeOfTracker) {
        this.typeOfTracker = typeOfTracker;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatusImage() {
        return syncStatusImage;
    }

    public void setSyncStatusImage(String syncStatusImage) {
        this.syncStatusImage = syncStatusImage;
    }

    public String getSelfie() {
        return selfie;
    }

    public void setSelfie(String selfie) {
        this.selfie = selfie;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getLocationTrack() {
        return locationTrack;
    }

    public void setLocationTrack(String locationTrack) {
        this.locationTrack = locationTrack;
    }

    @Override
    public String toString() {
        return "ReqSymtomBody{" +
                "localId='" + localId + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", typeOfTracker='" + typeOfTracker + '\'' +
                ", syncStatus='" + syncStatus + '\'' +
                ", syncStatusImage='" + syncStatusImage + '\'' +
                ", selfie='" + selfie + '\'' +
                ", message='" + message + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", locationTrack='" + locationTrack + '\'' +
                '}';
    }
}
