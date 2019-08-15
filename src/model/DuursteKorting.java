package model;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;

public class DuursteKorting implements Korting  {
    private Double korting;
    private boolean procent;


    public DuursteKorting(Double korting, String soort) {
        this.korting = korting;

        if (soort.equals("Procent")){
            procent = true;
        }
        else {
            procent = false;
        }
    }



    @Override
    public double kortingEuro(ObservableList<Savable2> artikels) {
        Double terug;
        Artikel artikel1 = new Artikel();
        artikel1.setVerkoopprijs("0");
        Artikel2 artikel2 = new Artikel2(artikel1);

        for (Savable2 artikel:artikels) {
            if(((Artikel2)artikel).getPrijs()>=(artikel2.getPrijs())){
                artikel2 = (Artikel2)artikel;
            }

        }
        if (procent){
            terug =  (artikel2.getVerkoopprijs())*korting/100;
        }else {
            terug = korting * artikel2.getAantal();
        }
        return terug;
    }
}
