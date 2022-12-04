package com.serhatozturk.yemekuygulamasi_10;

public class ModelYemek {


    String id;

    String sicil;
    String isim;
    String statu;
    String mudurluk;

    String yil;
    String ay;
    String gunler;

    String toplamGun;
    String birimUcret;
    String toplamUcret;

    public ModelYemek(String id, String sicil, String isim, String statu, String mudurluk, String yil, String ay, String gunler, String toplamGun, String birimUcret, String toplamUcret) {
        this.id = id;
        this.sicil = sicil;
        this.isim = isim;
        this.statu = statu;
        this.mudurluk = mudurluk;
        this.yil = yil;
        this.ay = ay;
        this.gunler = gunler;
        this.toplamGun = toplamGun;
        this.birimUcret = birimUcret;
        this.toplamUcret = toplamUcret;
    }
}
