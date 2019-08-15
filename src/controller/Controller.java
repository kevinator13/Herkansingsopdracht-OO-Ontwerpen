package controller;

import controller.DBContext;
import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Controller {
    private DBContext dbContext;
    private ObservableList<Savable> artikels = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Savable2> shoplist = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Korting> kortings = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Verkoop> verkoops = FXCollections.observableArrayList(new ArrayList<>());




    private PropertiesRekening properties = new PropertiesRekening();


    public Controller() {
        this.dbContext = new DBContext();
        this.readArtikels();

        artikels = this.getDbContext().getReadObjects();
        properties.readPropertyRekening();
    }

    public ObservableList<Savable> getArtikels() {
        return artikels;
    }

    public ObservableList<Savable2> getShop() {
        return shoplist;
    }

    public ObservableList<Korting> getKortings() {
        return kortings;
    }

    public ObservableList<Verkoop> getVerkoops() {
        return verkoops;
    }



    public HashMap<String, String> getMaptrue() {
        return properties.getMaptrue();
    }

    public HashMap<String, String> getMaptext() {
        return properties.getMaptext();
    }
    public DBContext getDbContext() {
        return dbContext;
    }
    // ROBIN beter naam && misschien beter in PropertiesRekening.
    public String getOutputProperty() {
        Properties properties = new Properties();
        InputStream is;
        File file;
        try {
            file = new File("resources/db/save.properties");
            is = this.getClass().getClassLoader().getResourceAsStream("resources/db/save.properties");
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch ( Exception e ) { is = null; }
        try {
            if ( is == null ) {
                is = getClass().getClassLoader().getResourceAsStream("resources/db/save.properties");
            }
            properties.load( is );
        }
        catch ( Exception e ) { }
        //--------------------------------------------------
        String evaluationMode = properties.getProperty("save.mode", "txt");
        return evaluationMode;
    }


    public ArrayList<String> getTextRekening(){
        return properties.getTextRekening();
    }

    public void writePropertiesRekening(HashMap<String, String> listtext){
        properties.writePropertiesRekening(listtext);
    }

    public void readArtikels(){
        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        dbContext.setStrategy(strategyFactory.getStrategy(getOutputProperty(), artikels));


        dbContext.read();
    }

    public void writeArtikels(){


        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        dbContext.setStrategy(strategyFactory.getStrategy(getOutputProperty(), artikels));


        dbContext.write();
    }

    public Double getKortingsprijs() {
        Double kortingprijs = 0.0;
        for (Korting korting: kortings) {
            kortingprijs += korting.kortingEuro(this.shoplist);
        }
        return kortingprijs;
    }

    public Double getprijstotaal() {
        Double prijs = 0.0;
        for (Savable2 savable2: shoplist) {
            prijs += ((Artikel2)savable2).getVerkoopprijs();
        }
        return prijs;
    }
}
