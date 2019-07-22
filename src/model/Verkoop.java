package model;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Verkoop
{
    private VerkoopState currentState;
    private LocalTime time;
    private LocalDate datum;
    private double totaal;
    private double korting;

    private ArrayList<Savable2> shoplist;
    private ArrayList<Savable> list;

    public Verkoop()
    {

        this.shoplist = new ArrayList<>();
        this.currentState = new VerkoopNew();
        this.datum = null;
        this.time = null;
        this.totaal = 0.0;
        this.korting = 0.0;

    }

    public void setState(VerkoopState state)
    {
        currentState = state;
    }

    public void setShoplist(ArrayList<Savable2> shoplist){
        this.shoplist = shoplist;

        this.totaal = 0.0;
        for (Savable2 artikel2: this.shoplist){
            this.totaal += ((Artikel2)artikel2).getVerkoopprijs();
        }

    }

    public void setArtikelList(ArrayList<Savable> list2) {
        this.list = list2;
    }

    public void setWaarden(Double totaal, Double korting){

        this.totaal = totaal;
        this.korting = korting;

    }

    public ArrayList<Savable2> getShoplist() {
        return shoplist;
    }

    public ArrayList<Savable> getList() {
        return list;
    }

    public void alert()
    {
        currentState.alert(this);
    }

    public void setMoment(VerkoopState state, Double totaal, Double korting){
        this.setState(state);
        this.setDatum();
        this.setTime();
        this.setWaarden(totaal, korting);
    }

    public void setDatum() {
        this.datum = LocalDate.now();
    }

    public void setTime() {
        this.time = LocalTime.now();
    }

    public LocalDate getDatum() {
        return datum;
    }

    public double getTotaal() {
        return totaal;
    }

    public double getKorting() {
        return korting;
    }

    public double getTebetalen() {
        return totaal-korting;
    }

    public LocalTime getTime() {
        return time;
    }
}
