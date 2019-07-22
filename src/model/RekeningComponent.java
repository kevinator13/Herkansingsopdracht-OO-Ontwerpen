package model;

import db.Savable2;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class RekeningComponent implements Rekening {
    private ObservableList<Savable2> shoplist;
    private ObservableList<Korting> kortings;
    private Double total;
    private Double korting;
    private Double totalWithKorting;

    public RekeningComponent(ObservableList<Savable2> shoplist, ObservableList<Korting> kortings) {
        this.shoplist = shoplist;
        this.kortings = kortings;
        this.setWaarden();
    }

    @Override
    public String rekeningFunctie() {



        String terug = String.format("%-20s", "Omschrijving") + String.format("%-8s", "aantal") + String.format("%-20s", "prijs")  + " \n ************************* \n";
        for (Savable2 artikel2:shoplist) {
            terug += String.format("%-20s", ((Artikel2)artikel2).getOmschrijving()) + String.format("%-8s", ((Artikel2)artikel2).getAantal()) + String.format("%-20s", ((Artikel2)artikel2).getPrijs()) +"\n";
        }
        terug += "*********************** \n";
        terug += "Betaald (Inclusief korting): " + totalWithKorting + "\n";
        return terug;
    }

    @Override
    public HashMap<String, Double> getWaarden() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("total", this.total);
        map.put("korting", this.korting);

        return map;
    }


    public void setWaarden() {
        this.total = 0.0;
        for (Savable2 artikel2: this.shoplist){
            this.total += ((Artikel2)artikel2).getVerkoopprijs();
        }
        this.korting = 0.0;
        for(Korting korting: kortings){
            this.korting += korting.kortingEuro(this.shoplist);
        }
        this.totalWithKorting = this.total - this.korting;
    }


}
