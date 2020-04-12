package com.goalsr.homequarantineTracker.resposemodel.getPatientinfo;

import com.goalsr.homequarantineTracker.resposemodel.SecurityObject;

public class ReqUpdatePatentInfo {

    private ResPatientInfo PersonDetails;
    private SecurityObject pSecurity;

    public ResPatientInfo getPersonDetails() {
        return PersonDetails;
    }

    public void setPersonDetails(ResPatientInfo personDetails) {
        PersonDetails = personDetails;
    }

    public SecurityObject getpSecurity() {
        return pSecurity;
    }

    public void setpSecurity(SecurityObject pSecurity) {
        this.pSecurity = pSecurity;
    }
}
