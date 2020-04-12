package com.goalsr.homequarantineTracker.resposemodel;

public class TalukModel {
    private String TALUKA_NAME;
    private String DISTRICT_NAME;
    private int TALUKA_CODE;
    private int district_code;

    public String getTALUKA_NAME() {
        return TALUKA_NAME;
    }

    public void setTALUKA_NAME(String TALUKA_NAME) {
        this.TALUKA_NAME = TALUKA_NAME;
    }

    public String getDISTRICT_NAME() {
        return DISTRICT_NAME;
    }

    public void setDISTRICT_NAME(String DISTRICT_NAME) {
        this.DISTRICT_NAME = DISTRICT_NAME;
    }

    public int getTALUKA_CODE() {
        return TALUKA_CODE;
    }

    public void setTALUKA_CODE(int TALUKA_CODE) {
        this.TALUKA_CODE = TALUKA_CODE;
    }

    public int getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(int district_code) {
        this.district_code = district_code;
    }
}
