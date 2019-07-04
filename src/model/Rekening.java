package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Rekening {
    private double total;
private ArrayList<Artikel> list;

    public Rekening() {
        total = 0;
        list = new ArrayList<Artikel>();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<Artikel> getList() {
        return list;
    }

    public void setList(ArrayList<Artikel> list) {
        this.list = list;
    }

    public void addArtikel(Artikel artikel){
        total += artikel.getVerkoopprijs();
        list.add(artikel);

    }

    public void removeArtikel(Artikel artikel){
        total -= artikel.getVerkoopprijs();
        list.remove(artikel);

    }


}
