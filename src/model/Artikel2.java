package model;

import db.Savable;
import db.Savable2;

import java.util.Observable;

public class Artikel2 implements Savable2  {
    private String omschrijving;
    private double prijs;
    private double verkoopprijs;
    private int aantal;
    private String groep;

    public Artikel2(Artikel artikel) {
        this.omschrijving = artikel.getOmschrijving();
        this.verkoopprijs = artikel.getVerkoopprijs();
        this.prijs = artikel.getVerkoopprijs();
        this.aantal = 1;
        this.groep = artikel.getArtikelgroep();
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public double getVerkoopprijs() {
        return verkoopprijs;
    }

    public int getAantal() {
        return aantal;
    }

    public double getPrijs() {
        return prijs;
    }

    public String getGroep() {
        return groep;
    }

    public void addMore(){
        this.aantal = this.aantal + 1;
        this.verkoopprijs += this.prijs;
    }

    public void removeOne() {
        this.aantal = this.aantal - 1;
        this.verkoopprijs -= this.prijs;
    }

}
