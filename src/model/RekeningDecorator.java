package model;

import db.Savable2;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

public class RekeningDecorator implements Rekening {
    private Rekening rekening;
    private HashMap<String, Double> map;




    public RekeningDecorator(Rekening rekening) {
        this.rekening=rekening;
        this.map = this.rekening.getWaarden();


    }


    @Override
    public String rekeningFunctie() {
        return rekening.rekeningFunctie();
    }

    @Override
    public HashMap<String, Double> getWaarden() {
        return map;
    }

    public Double getTotal() {
        if (this.map.containsKey("total")){
            return this.map.get("total");
        } else {
            return 0.0;
        }

    }

    public Double getKorting() {

        if (this.map.containsKey("korting")) {
            return this.map.get("korting");
        } else {
            return 0.0;
        }
    }


}
