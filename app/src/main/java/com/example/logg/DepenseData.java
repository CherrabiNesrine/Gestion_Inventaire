package com.example.logg;

public class DepenseData {

    String NOM,MAGASIN,DESCRIPTION,DateCrea,AR,id;
    double TOTAL;


    public String getID() {
        return id;
    }
    public void setID(String I) {
        this.id = I;
    }
    public double getTotal() {
        return TOTAL;
    }
    public void setTOTAL(double total) {
        this.TOTAL = total;
    }

    public String getAr() {
        return AR;
    }
    public void setAR(String ar) {
        this.AR = ar;
    }
    public String getNOM() {
        return NOM;
    }
    public void setNOM(String nom) {
        this.NOM = nom;
    }

    public String getMAGASIN() {
        return MAGASIN;
    }
    public void setMAGASIN(String magasin) {
        this.MAGASIN = magasin;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }
    public void setDESCRIPTION(String DES) {
        this.DESCRIPTION = DES;
    }

    public String getDateCrea() {
        return DateCrea;
    }
    public void setDateCrea(String dateCrea) {
        this.DateCrea = dateCrea;
    }

}
