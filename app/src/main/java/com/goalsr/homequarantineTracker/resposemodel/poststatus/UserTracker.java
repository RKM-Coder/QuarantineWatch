package com.goalsr.homequarantineTracker.resposemodel.poststatus;

import java.util.ArrayList;

public class UserTracker {
    private ArrayList<ReqSymtomBody> userTrackData;

    public ArrayList<ReqSymtomBody> getUserTrackData() {
        return userTrackData;
    }

    public void setUserTrackData(ArrayList<ReqSymtomBody> userTrackData) {
        this.userTrackData = userTrackData;
    }
}
