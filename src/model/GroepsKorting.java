package model;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class GroepsKorting implements Korting  {
    private Double korting;
    private boolean procent;
    private String groep;

    public GroepsKorting(Double korting, String soort, String groep) {
        this.korting = korting;
        this.groep = groep;
        if (soort.equals("Procent")){
            procent = true;
        }
        else {
            procent = false;
        }
    }



    @Override
    public double kortingEuro(ObservableList<Savable2> list) {
        double terug = 0;
        for (Savable2 artikel: list){
            if (((Artikel2)artikel).getGroep().equals(this.groep)){

                if (procent){
                    terug += (((Artikel2)artikel).getVerkoopprijs()/100)*korting;
                } else {
                    terug += (korting * ((Artikel2)artikel).getAantal());
                }

            }
        }
        return terug;
    }
}