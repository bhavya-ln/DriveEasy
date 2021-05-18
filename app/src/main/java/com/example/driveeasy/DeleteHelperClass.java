package com.example.driveeasy;

public class DeleteHelperClass {
    String loc, b1, b2, d1,d2,c1,c2;
    public DeleteHelperClass() {

    }
    public DeleteHelperClass(String loc, String b1, String b2, String d1,String d2,String c1,String c2) {
        this.loc = loc;
        this.b1 = b1;
        this.b2 = b2;
        this.d1 = d1;
        this.d2 = d2;
        this.c1 = c1;
        this.c2 = c2;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b1) { this.b2 = b2; }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }
    public String getc1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }
    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }
}
