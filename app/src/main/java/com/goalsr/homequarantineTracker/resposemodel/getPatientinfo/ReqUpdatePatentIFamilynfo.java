package com.goalsr.homequarantineTracker.resposemodel.getPatientinfo;

import com.goalsr.homequarantineTracker.resposemodel.SecurityObject;

public class ReqUpdatePatentIFamilynfo {

    private ResPatientFamilyInfo FamilyDetails;
    private SecurityObject pSecurity;

    public ResPatientFamilyInfo getFamilyDetails() {
        return FamilyDetails;
    }

    public void setFamilyDetails(ResPatientFamilyInfo familyDetails) {
        FamilyDetails = familyDetails;
    }

    public SecurityObject getpSecurity() {
        return pSecurity;
    }

    public void setpSecurity(SecurityObject pSecurity) {
        this.pSecurity = pSecurity;
    }
}
