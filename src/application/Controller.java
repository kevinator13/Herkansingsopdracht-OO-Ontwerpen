package application;

import controller.DBContext;
import db.*;
import javafx.collections.ObservableList;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Controller {
    private DBContext dbContext;
    private ObservableList<Savable> artikels;
    private ObservableList<Savable2> shop;
    private ObservableList<Korting> kortings;
    private ObservableList<Verkoop> verkoops;
    private ObservableList<RekeningInstelling> rekenings;



    private PropertiesRekening properties = new PropertiesRekening();


    public Controller(DBContext artikelDbContext, ObservableList<Savable> artikels, ObservableList<Savable2> shop, ObservableList<Korting> kortings, ObservableList<Verkoop> verkoops, ObservableList<RekeningInstelling> rekenings) {
        this.dbContext = artikelDbContext;
        this.artikels = artikels;
        this.shop = shop;
        this.kortings = kortings;
        this.verkoops = verkoops;
        this.rekenings = rekenings;
        this.readPropertyRekening();

    }

    public ObservableList<Savable> getArtikels() {
        return artikels;
    }

    public ObservableList<Savable2> getShop() {
        return shop;
    }

    public ObservableList<Korting> getKortings() {
        return kortings;
    }

    public ObservableList<Verkoop> getVerkoops() {
        return verkoops;
    }

    public ObservableList<RekeningInstelling> getRekenings() {
        return rekenings;
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

    public String getProperty() {
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

    public void readPropertyRekening() {
        properties.readPropertyRekening();

    }
    public ArrayList<String> getTextRekening(){
        return properties.getTextRekening();
    }

    public void writePropertiesRekening(HashMap<String, String> listtext){
        properties.writePropertiesRekening(listtext);
    }

    public void writeArtikels(){


        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        dbContext.setStrategy(strategyFactory.getStrategy(getProperty(), artikels));


        dbContext.write();
    }

    public void readArtikels(){
        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        dbContext.setStrategy(strategyFactory.getStrategy(getProperty(), artikels));


        dbContext.read();
    }

    public Double getKortingsprijs() {
        Double kortingprijs = 0.0;
        for (Korting korting: kortings) {
            kortingprijs += korting.kortingEuro(this.shop);
        }
        return kortingprijs;
    }

    public Double getprijstotaal() {
        Double prijs = 0.0;
        for (Savable2 savable2: shop) {
            prijs += ((Artikel2)savable2).getVerkoopprijs();
        }
        return prijs;
    }
}
