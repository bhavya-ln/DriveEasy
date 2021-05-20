package com.example.driveeasy;

public class DashboardHelperClass {
    String date, locSet;
    int noD;

    public DashboardHelperClass() {

    }

    public DashboardHelperClass(String date, int noD, String locSet) {
        this.date = date;
        this.noD = noD;
        this.locSet = locSet;

    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public int getnoD() {
        return noD;
    }

    public void setnoD(int noD) {
        this.noD = noD;
    }

    public String getlocSet() {
        return locSet;
    }

    public void setlocSet(String locSet) {
        this.locSet = locSet;
    }

}