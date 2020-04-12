package com.goalsr.homequarantineTracker.ui;

public class SymtomModel {

    private String strname;
    private int iconamker;

    public SymtomModel(String strname, int iconamker) {
        this.strname = strname;
        this.iconamker = iconamker;
    }

    public String getStrname() {
        return strname;
    }

    public void setStrname(String strname) {
        this.strname = strname;
    }

    public int getIconamker() {
        return iconamker;
    }

    public void setIconamker(int iconamker) {
        this.iconamker = iconamker;
    }
}
