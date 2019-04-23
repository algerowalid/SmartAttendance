package com.example.admin.synchronisation_tstart.affichage;

public class list_contact {


    int id, group;
    String nom, prenom , adresse, telephone, idcarte;


    public list_contact(int id, String nom, String prenom, String adresse, String telephone,String idc, int grp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.idcarte= idc;
        this.group= grp;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getIdcarte() {
        return idcarte;
    }

    public void setIdcarte(String idcarte) {
        this.idcarte = idcarte;
    }

    public String getNom2() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
