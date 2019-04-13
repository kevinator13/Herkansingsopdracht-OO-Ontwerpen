package controller;

import db.ArtikelTXT;
import db.DBStrategy;
import db.Savable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Artikel;

import java.util.ArrayList;

public class DBContext implements DBStrategy {
    private DBStrategy strategy;

    public DBContext()
    {

    }

    public void setStrategy(DBStrategy strat)
    {
        this.strategy=strat;
    }

    @Override
    public void write() {
        strategy.write();
    }

    @Override
    public void read() {
        strategy.read();
    }

    @Override
    public ObservableList<Savable> getReadObjects() {
        return strategy.getReadObjects();
    }

    public void run()
    {
        DBContext context = new DBContext();
        ObservableList<Savable> artikels = FXCollections.observableArrayList(new ArrayList<>());
        Artikel c1 = new Artikel("1","artikel1","gr1", "12.5", "10");
        Artikel c2 = new Artikel("2","artikel2","gr1", "22.4", "10");
        Artikel c3 = new Artikel("3","bartikel3","gr1", "12.5", "10");
        artikels.add(c1);
        artikels.add(c2);
        artikels.add(c3);


        context.setStrategy(new ArtikelTXT(artikels));
        context.write();
        context.read();

    }
}
