package com.example.admin.synchronisation_tstart.affichage;

public class programme {

    int id , idc;
    String module, designation , hdd, hf ;



    public programme(int id, int idc, String module, String designation, String hdd, String hf) {
        this.id = id;
        this.idc = idc;
        this.module = module;
        this.designation = designation;
        this.hdd = hdd;
        this.hf = hf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getHf() {
        return hf;
    }

    public void setHf(String hf) {
        this.hf = hf;
    }
}
