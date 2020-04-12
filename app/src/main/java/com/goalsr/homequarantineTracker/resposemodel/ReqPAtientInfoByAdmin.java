package com.goalsr.homequarantineTracker.resposemodel;

public class ReqPAtientInfoByAdmin {

    private int DistCode;
    private SecurityObject pSecurity;

    public int getDistCode() {
        return DistCode;
    }

    public void setDistCode(int distCode) {
        DistCode = distCode;
    }

    public SecurityObject getpSecurity() {
        return pSecurity;
    }

    public void setpSecurity(SecurityObject pSecurity) {
        this.pSecurity = pSecurity;
    }
}
