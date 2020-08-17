package com.example.logg;

public class Magasin {
    private String nomMag,typMag,unitMag;

    Magasin(){
        nomMag=typMag=unitMag="";

    }

    public String getNomMag() {
        return nomMag;
    }

    public String getTypMag() {
        return typMag;
    }

    public String getUnitMag() {
        return unitMag;
    }

    public void setNomMag(String nomMag) {
        this.nomMag = nomMag;
    }

    public void setTypMag(String typMag) {
        this.typMag = typMag;
    }

    public void setUnitMag(String unitMag) {
        this.unitMag = unitMag;
    }
}
