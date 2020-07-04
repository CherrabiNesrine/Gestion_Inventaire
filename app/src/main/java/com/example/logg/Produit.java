package com.example.logg;

import android.text.Editable;

import java.util.Date;

public class Produit {
    private String Code,name,factory,description,categorie,sous,matiere;
    private int quantite ;
    private double price;
    private Date Exp =new Date();
    private Date Fab=new Date();
    private byte[] image;
    public Produit(){
        Code=name=factory=description=matiere="";
        quantite=123;
        Fab.setYear(2000);
        Fab.setMonth(01);
        Fab.setDate(01);

        Exp.setYear(2000);
        Exp.setMonth(01);
        Exp.setDate(01);
        price=0;

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

   /* public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }



    public String getMesure() {
        return mesure;
    }

    public void setMesure(String mesure) {
        this.mesure = mesure;
    }*/
   public String getMatiere(){
       return matiere;
   }
    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getSous() {
        return sous;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setSous(String sous) {
        this.sous = sous;
    }

    public String getCode(){
        return Code;
    }
    public void setCode(String code){
        this.Code=code;

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getDescription(){
        return description;
    }
public void setDescription(String description){
        this.description=description;
    }
    public String getFactory(){
        return factory;
    }
    public void setFactory(String factory){
        this.factory=factory;
    }

    public Date getExp() {
        return Exp;
    }

    public Date getFab() {
        return Fab;
    }

    public double getPrice() {
        return price;
    }

    public void setExp(Date exp) {
        Exp = exp;
    }

    public void setFab(Date fab) {
        Fab = fab;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
