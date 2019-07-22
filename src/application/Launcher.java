package application;

import controller.DBContext;
import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.*;
import view.*;


import java.util.*;

public class Launcher {
    private Controller controller;
    private DBContext artikelDbContext;
    private ObservableList<Savable> artikels = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Savable2> shop = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Korting> kortings = FXCollections.observableArrayList(new ArrayList<>());

    private ObservableList<Verkoop> verkoops = FXCollections.observableArrayList(new ArrayList<>());
    private Klant klant = new Klant();

    private ObservableList<RekeningInstelling> rekenings = FXCollections.observableArrayList(new ArrayList<>());



    public Launcher()
    {

    }

    public void start(Stage primaryStage)
    {



        try {
            this.artikelDbContext = new DBContext();

            this.controller = new Controller(artikelDbContext, artikels, shop, kortings, verkoops, rekenings);


            this.controller.readArtikels();

            artikels = controller.getDbContext().getReadObjects();





            DBContext context = new DBContext();
            if(artikels == null || artikels.size() == 0) {context.run();}

            this.controller = new Controller(artikelDbContext, artikels, shop, kortings, verkoops, rekenings);

            KassaPane kassaPane = new KassaPane(controller, klant);
            ArtikelOverviewPane artikelOverviewPane = new ArtikelOverviewPane(artikels);
            InstellingenPane instellingenPane = new InstellingenPane(this.controller);
            LogPane logPane = new LogPane(verkoops);





            KassaView kassaView= new KassaView(primaryStage, kassaPane, artikelOverviewPane, instellingenPane, logPane);
            KlantView klantView = new KlantView(shop, kortings, klant);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
