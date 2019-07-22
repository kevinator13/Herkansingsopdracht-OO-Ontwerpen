package model;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;

public class DrempelKorting implements Korting  {
    private Double korting;
    private Double regel;

    private boolean procent;

    public DrempelKorting(Double korting, String soort, String regel) {
        this.korting = korting;

        this.regel = Double.parseDouble(regel);
        if (soort.equals("Procent")){
            procent = true;
        }
        else {
            procent = false;
        }
    }



    @Override
    public double kortingEuro(ObservableList<Savable2> artikels) {
        double terug = 0;
        double kortingterug = 0;
        for (Savable2 artikel:artikels) {
            terug += ((Artikel2)artikel).getVerkoopprijs();
        }
        if (terug>=regel){
            if (procent){
                kortingterug = (terug/100)*this.korting;
            }else {
                kortingterug = this.korting;
            }
        } else {
            kortingterug = 0;
        }
        return kortingterug;
    }
}
