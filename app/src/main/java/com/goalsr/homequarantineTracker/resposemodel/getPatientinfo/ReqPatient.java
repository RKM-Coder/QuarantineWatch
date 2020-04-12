package com.goalsr.homequarantineTracker.resposemodel.getPatientinfo;

import com.goalsr.homequarantineTracker.resposemodel.SecurityObject;

public class ReqPatient {

    private int CitizenId;
    private int Level;
    private SecurityObject pSecurity;

    public int getCitizenId() {
        return CitizenId;
    }

    public void setCitizenId(int citizenId) {
        CitizenId = citizenId;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public SecurityObject getpSecurity() {
        return pSecurity;
    }

    public void setpSecurity(SecurityObject pSecurity) {
        this.pSecurity = pSecurity;
    }
}
