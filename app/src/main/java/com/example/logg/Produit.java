package com.example.logg;

import android.text.Editable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Produit {
    private String Code,name,factory,description,categorie,sous,mesure,typePr,fournisseur,client,coin,coins,matiere,MAG;
    private int quantite ,qttmin;
    private double price,prixS;
    private Date Exp =new Date();
    private Date Fab=new Date();
    private Date Ent=new Date();
    private Date dateModif= new Date();
    private Date dateDel;
    private byte[] image;
    public Produit() {
        Code=name=MAG=factory=description=mesure=typePr=categorie=sous=fournisseur=client=matiere="";
        quantite=123;
        qttmin=0;
        Fab.setYear(2000);
        Fab.setMonth(01);
        Fab.setDate(01);
        Ent.setYear(2000);
        Ent.setMonth(01);
        Ent.setDate(01);
        Exp.setYear(2000);
        Exp.setMonth(01);
        Exp.setDate(01);
         dateDel = new Date();
        price=0;
        prixS=0;
        coins=coin="Algerian dinar";

    }

    public String getMAG() {
        return MAG;
    }

    public void setMAG(String MAG) {
        this.MAG = MAG;
    }

    public Date getDateDel() {
        return dateDel;
    }

    public Date getDateModif() {
        return dateModif;
    }

    public void setDateDel(Date dateDel) {
        this.dateDel = dateDel;
    }

    public void setDateModif(Date dateModif) {
        this.dateModif = dateModif;
    }

    public int getQttmin() {
        return qttmin;
    }

    public void setQttmin(int qttmin) {
        this.qttmin = qttmin;
    }

    public double getPrixS() {
        return prixS;
    }

    public String getCoins() {
        return coins;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public void setPrixS(double prixS) {
        this.prixS = prixS;
    }

    public Date getEnt() {
        return Ent;
    }

    public String getClient() {
        return client;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public String getTypePr() {
        return typePr;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setEnt(Date ent) {
        Ent = ent;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public void setTypePr(String typePr) {
        this.typePr = typePr;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

   public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
   public String getmesure(){
       return mesure;
   }
    public void setmesure(String mesure) {
        this.mesure = mesure;
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
