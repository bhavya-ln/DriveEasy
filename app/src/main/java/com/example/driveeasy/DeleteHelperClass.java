package com.example.driveeasy;

public class DeleteHelperClass {
    String Name, NoS, NumPlate, FT,ImgID,Price;
    public DeleteHelperClass() {

    }
    public DeleteHelperClass(String Name, String NoS, String NumPlate, String FT,String ImgID,String Price) {
        this.Name = Name;
        this.NoS = NoS;
        this.NumPlate = NumPlate;
        this.FT = FT;
        this.ImgID = ImgID;
        this.Price = Price;

    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNoS() {
        return NoS;
    }

    public void setNoS(String NoS) {
        this.NoS = NoS;
    }

    public String getNumPlate() {
        return NumPlate;
    }

    public void setNumPlate(String NoS) { this.NumPlate = NumPlate; }

    public String getFT() {
        return FT;
    }

    public void setFT(String FT) {
        this.FT = FT;
    }

    public String getImgID() {
        return ImgID;
    }

    public void setImgID(String ImgID) {
        this.ImgID = ImgID;
    }
    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    }

