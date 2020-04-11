package com.wirnin.hanguldetection.Activity;

public class SoalLatihan {
    private String id;
    private String hangeul;
    private String gambar;
    private String pilihan1;
    private String pilihan2;
    private String pilihan3;

    public SoalLatihan(String id, String hangeul, String gambar, String pilihan1, String pilihan2, String pilihan3){
        this.id = id;
        this.hangeul = hangeul;
        this.gambar = gambar;
        this.pilihan1 = pilihan1;
        this.pilihan2 = pilihan2;
        this.pilihan3 = pilihan3;
    }

    public SoalLatihan(){

    }

    public String getPilihan1() {
        return pilihan1;
    }

    public void setPilihan1(String pilihan1) {
        this.pilihan1 = pilihan1;
    }

    public String getPilihan2() {
        return pilihan2;
    }

    public void setPilihan2(String pilihan2) {
        this.pilihan2 = pilihan2;
    }

    public String getPilihan3() {
        return pilihan3;
    }

    public void setPilihan3(String pilihan3) {
        this.pilihan3 = pilihan3;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHangeul() {
        return hangeul;
    }

    public void setHangeul(String hangeul) {
        this.hangeul = hangeul;
    }
}
