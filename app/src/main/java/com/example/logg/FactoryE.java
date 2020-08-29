package com.example.logg;

public class FactoryE {
    private String email,adress,name,prenom,type,phone,facebook,LinkedIn,twitter,comEmail,statujur,secteur,taille,Nif,Rg,Address,Nom,Prenom,phone_num,job,Fax,Site;
    private byte[] factlogo,image;
    private double reduction;
    FactoryE(){
        reduction=0.0;
        email=adress=prenom=name=phone=Nom=Prenom=type=job=facebook=LinkedIn=twitter=comEmail=statujur=secteur=taille=Nif=Rg=Address=phone_num=Fax=Site="";
    }
    public String getComEmail() {
        return comEmail;
    }

    public String getType() {
        return type;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComEmail(String comEmail) {
        this.comEmail = comEmail;
    }

    public byte[] getImage() {
        return image;
    }

    public String getNif() {
        return Nif;
    }

    public String getRg() {
        return Rg;
    }

    public String getSecteur() {
        return secteur;
    }

    public String getStatujur() {
        return statujur;
    }

    public String getTaille() {
        return taille;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setLinkedIn(String linkedIn) {
        LinkedIn = linkedIn;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setNif(String nif) {
        Nif = nif;
    }
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setRg(String rg) {
        Rg = rg;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public void setStatujur(String statujur) {
        this.statujur = statujur;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }


    public String getAddress() {
        return Address;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getFax() {
        return Fax;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return LinkedIn;
    }



    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }



    public byte[] getFactlogo() {
        return factlogo;
    }

    public void setFactlogo(byte[] factlogo) {
        this.factlogo = factlogo;
    }

    public String getAdress() {
        return adress;
    }

    public String getNom() {
        return Nom;
    }

    public String getJob() {
        return job;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
