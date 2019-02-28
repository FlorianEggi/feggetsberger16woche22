package com.example.feggetsberger16.feggetsberger16woche22;

import java.time.LocalDate;

/**
 * Created by feggetsberger16 on 28.02.2019.
 */

public class Eintrag {
    LocalDate date;
    String art;
    double price;
    String kat;

    public Eintrag(LocalDate date, String art, double price, String kat) {
        this.date = date;
        this.art = art;
        this.price = price;
        this.kat = kat;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getKat() {
        return kat;
    }

    public void setKat(String kat) {
        this.kat = kat;
    }
}
