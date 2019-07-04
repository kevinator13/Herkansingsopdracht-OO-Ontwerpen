package model;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;

public class DuursteKorting implements Korting  {
    private Double korting;
    private boolean procent;
    private ObservableList<Savable2> list;

    public DuursteKorting(Double korting, ObservableList<Savable2> artikels, String soort) {
        this.korting = korting;
        list = artikels;
        if (soort.equals("Procent")){
            procent = true;
        }
        else {
            procent = false;
        }
    }



    @Override
    public double kortingEuro() {
        Double terug = null;
        Artikel artikel1 = new Artikel();
        artikel1.setVerkoopprijs("0");
        Artikel2 artikel2 = new Artikel2(artikel1);

        for (Savable2 artikel:list) {
            if(((Artikel2)artikel).getPrijs()>=(artikel2.getPrijs())){
                artikel2 = (Artikel2)artikel;
            }

        }
        if (procent){
            terug =  (artikel2.getVerkoopprijs()/100)*korting;
        }else {
            terug = korting * artikel2.getAantal();
        }
        return terug;
    }
}
