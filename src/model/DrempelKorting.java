package model;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;

public class DrempelKorting implements Korting  {
    private Double korting;
    private Double regel;
    private ObservableList<Savable2> list;
    private boolean procent;

    public DrempelKorting(Double korting, ObservableList<Savable2> artikels, String soort) {
        this.korting = korting;
        list = artikels;
        this.regel = 100.0;
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

        for (Savable2 artikel:list) {
            if(((Artikel2)artikel).getVerkoopprijs()>=(regel)){
                if (procent){
                    terug += ((((Artikel2)artikel).getVerkoopprijs()/100)*korting);
                }else {
                    terug += korting ;
                }

            }

        }
        return terug;
    }
}
