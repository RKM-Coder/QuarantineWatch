package com.goalsr.homequarantineTracker.resposemodel;

public class ReqGvtPatientFamillySymptom {

    private int CitizenID;;
    private int FamilyPersonId;;
    private boolean fever;
    private boolean CoughSourThroat;
    private boolean BreathingProblem;
    private boolean Diarrhea;
    private boolean Diabetes;
    private boolean Hypertension;
    private boolean HeartIssue;
    private boolean HIV;
    private int UBy;
    private int RoleId;
    private String AdditionalInfo;
    private SecurityObject pSecurity;


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

    public int getFamilyPersonId() {
        return FamilyPersonId;
    }

    public void setFamilyPersonId(int familyPersonId) {
        FamilyPersonId = familyPersonId;
    }

    public int getCitizenID() {
        return CitizenID;
    }

    public void setCitizenID(int citizenID) {
        CitizenID = citizenID;
    }

    public boolean isFever() {
        return fever;
    }

    public void setFever(boolean fever) {
        this.fever = fever;
    }

    public boolean isCoughSourThroat() {
        return CoughSourThroat;
    }

    public void setCoughSourThroat(boolean coughSourThroat) {
        CoughSourThroat = coughSourThroat;
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

    public int getUBy() {
        return UBy;
    }

    public void setUBy(int UBy) {
        this.UBy = UBy;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
    }

    public SecurityObject getpSecurity() {
        return pSecurity;
    }

    public void setpSecurity(SecurityObject pSecurity) {
        this.pSecurity = pSecurity;
    }
}
