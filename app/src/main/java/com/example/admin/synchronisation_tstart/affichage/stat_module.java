package com.example.admin.synchronisation_tstart.affichage;

public class stat_module {

    int id;
    String nom,prenom,  carte, telephone;
    int  presence, total;


    public stat_module(int id, String nom, String prenom ,String carte, String telephone, int presence, int total) {
        this.id = id;
        this.nom = nom;
        this.carte = carte;
        this.telephone = telephone;
        this.presence = presence;
        this.total = total;
        this.prenom  = prenom;
    }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCarte() {
        return carte;
    }

    public void setCarte(String carte) {
        this.carte = carte;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
