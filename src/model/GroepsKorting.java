package model;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class GroepsKorting implements Korting  {
    private Double korting;
    private ObservableList<Savable2> list;
    private ArrayList<String> omschrijvinglist;


    private boolean procent;

    public GroepsKorting(Double korting, ObservableList<Savable2> list, String soort) {
        this.korting = korting;
        this.list = list;
        if (soort.equals("Procent")){
            procent = true;
        }
        else {
            procent = false;
        }
    }



    @Override
    public double kortingEuro() {
        double terug = 0;
        for (Savable2 artikel: list){
            for (String omschrijving: omschrijvinglist){
                if (((Artikel2)artikel).getOmschrijving().equals(omschrijving)){
                    if (procent){
                        terug += (((Artikel2)artikel).getVerkoopprijs()/100)*korting;
                    } else {
                        terug += (korting * ((Artikel2)artikel).getAantal());
                    }
                }
            }
        }
        return terug;
    }
}