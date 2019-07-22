package model;

public class RekeningBTW extends RekeningDecorator {
    private Double btw;
    public RekeningBTW(Rekening rekening) {
        super(rekening);
        this.btw = (super.getTotal() /100)*6;
    }

    public String rekeningFunctie(){
        return super.rekeningFunctie() + this.rekeningFunctieAlgemeen();
    }

    private String rekeningFunctieAlgemeen() {
        return "Exclusief BTW :" + (super.getTotal() - btw) + "\n" + "Btw is 6% voor alle artikelen \n";
    }


}
