package application;

import controller.Controller;
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
    private ObservableList<Savable> artikels;
    private ObservableList<Savable2> shop;
    private ObservableList<Korting> kortings;

    private ObservableList<Verkoop> verkoops;
    private Klant klant = new Klant();
// alles in controller




    public Launcher()
    {

    }

    public void start(Stage primaryStage)
    {



        try {
            this.controller = new Controller();

            artikels = controller.getArtikels();





            DBContext context = new DBContext();
            if(artikels == null || artikels.size() == 0) {context.run();}



            KassaPane kassaPane = new KassaPane(this.controller, klant);
            ArtikelOverviewPane artikelOverviewPane = new ArtikelOverviewPane(this.controller);
            InstellingenPane instellingenPane = new InstellingenPane(this.controller);
            LogPane logPane = new LogPane(this.controller);





            new KassaView(primaryStage, kassaPane, artikelOverviewPane, instellingenPane, logPane);
            new KlantView(this.controller, klant);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
