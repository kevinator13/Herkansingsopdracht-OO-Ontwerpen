package model;

import javafx.collections.ObservableList;

public class RekeningZonderKorting extends RekeningDecorator {

    public RekeningZonderKorting(Rekening rekening) {
        super(rekening);

    }

    public String rekeningFunctie(){
        return super.rekeningFunctie() + this.rekeningFunctieAlgemeen();
    }

    private String rekeningFunctieAlgemeen() {
        return "total: " + super.getTotal() + " korting: " + super.getKorting() + "\n";
    }


}
