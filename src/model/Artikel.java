package model;

import db.Savable;

public class Artikel implements Savable {
    private int code;
    private String omschrijving;
    private String artikelgroep;
    private double verkoopprijs;
    private int voorraad;

    public Artikel(String code, String omschrijving, String artikelgroep, String verkoopprijs, String voorraad) {
        setCode(code);
        setOmschrijving(omschrijving);
        setArtikelgroep(artikelgroep);
        setVerkoopprijs(verkoopprijs);
        setVoorraad(voorraad);
    }

    public Artikel() {
    }

    public void setCode(String code) {

        this.code = Integer.parseInt(code);
    }

    public void  verlaagVoorraad(int aantal){
        voorraad -= aantal;

    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public void setArtikelgroep(String artikelgroep) {
        this.artikelgroep = artikelgroep;
    }

    public void setVerkoopprijs(String verkoopprijs) {
        this.verkoopprijs = Double.parseDouble(verkoopprijs);
    }

    public void setVoorraad(String voorraad) {
        this.voorraad = Integer.parseInt(voorraad);
    }

    public int getCode() {
        return code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public String getArtikelgroep() {
        return artikelgroep;
    }

    public double getVerkoopprijs() {
        return verkoopprijs;
    }

    public int getVoorraad() {
        return voorraad;
    }
}
