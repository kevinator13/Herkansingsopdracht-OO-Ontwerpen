package application;

import controller.DBContext;
import db.ArtikelTXT;
import db.Savable;
import db.Savable2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Artikel2;
import model.Klant;
import model.Korting;
import view.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class Launcher {
    private DBContext artikelDbContext;
    private ObservableList<Savable> artikels = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Savable2> shop = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<Korting> kortings = FXCollections.observableArrayList(new ArrayList<>());
private Klant klant = new Klant();
    private Klant klant2 = new Klant();
    public Launcher()
    {

    }

    public void start(Stage primaryStage)
    {
        try {
            this.artikelDbContext = new DBContext();

            this.artikelDbContext.setStrategy(new ArtikelTXT( artikels));

            artikelDbContext.read();

            artikels = artikelDbContext.getReadObjects();
            System.out.println(artikels);


            DBContext context = new DBContext();
            if(artikels == null || artikels.size() == 0) {context.run();}

            KassaPane kassaPane = new KassaPane(artikels, shop, kortings);
            ArtikelOverviewPane artikelOverviewPane = new ArtikelOverviewPane(artikels);
InstellingenPane instellingenPane = new InstellingenPane(shop, kortings);






            Group root = new Group();
            Scene scene = new Scene(root, 750, 400);

            BorderPane borderPane = new KassaMainPane(kassaPane, artikelOverviewPane, instellingenPane);

            borderPane.prefHeightProperty().bind(scene.heightProperty());
            borderPane.prefWidthProperty().bind(scene.widthProperty());

            root.getChildren().add(borderPane);

            primaryStage.setScene(scene);
            primaryStage.sizeToScene();

            primaryStage.show();
            KlantView klantView = new KlantView(shop, kortings);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
