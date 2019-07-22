package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RekeningDatum extends RekeningDecorator {
    public RekeningDatum(Rekening rekening) {
        super(rekening);
    }

    public String rekeningFunctie(){
        return  this.rekeningFunctieAlgemeen() + super.rekeningFunctie();
    }

    private String rekeningFunctieAlgemeen() {
        return "Datum: " + LocalDate.now() + "; Time: " + LocalTime.now() + "\n";
    }


}
