package model;

public class RekeningAlgemeneBoodschap extends RekeningDecorator {
    private String boodschap;
    public RekeningAlgemeneBoodschap(Rekening rekening, String boodschap) {
        super(rekening);
        this.boodschap = boodschap;
    }

    public String rekeningFunctie(){
        return  this.rekeningFunctieAlgemeen() + super.rekeningFunctie();
    }

    private String rekeningFunctieAlgemeen() {
        return "AlgemeneBoodschap: " +boodschap + "\n";
    }


}
