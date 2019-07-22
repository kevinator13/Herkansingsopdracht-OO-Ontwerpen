package model;

public class RekeningAlgemeneAfsluitlijn extends RekeningDecorator {
    private String boodschap;
    public RekeningAlgemeneAfsluitlijn(Rekening rekening, String boodschap) {
        super(rekening);
        this.boodschap = boodschap;
    }

    public String rekeningFunctie(){
        return  super.rekeningFunctie() + this.rekeningFunctieAlgemeen();
    }

    private String rekeningFunctieAlgemeen() {
        return "AlgemeneAfsluitboodschap: " + this.boodschap + "\n";
    }


}
