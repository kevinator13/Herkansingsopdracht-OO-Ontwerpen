package application;

import controller.DBContext;
import db.ArtikelTXT;
import db.Savable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.ArtikelOverviewPane;
import view.KassaMainPane;

import java.io.IOException;
import java.util.ArrayList;

public class Launcher {
    private DBContext artikelDbContext;
    private ObservableList<Savable> artikels = FXCollections.observableArrayList(new ArrayList<>());

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

            ArtikelOverviewPane artikelOverviewPane = new ArtikelOverviewPane(artikels);






            Group root = new Group();
            Scene scene = new Scene(root, 750, 400);

            BorderPane borderPane = new KassaMainPane(artikelOverviewPane);
            borderPane.prefHeightProperty().bind(scene.heightProperty());
            borderPane.prefWidthProperty().bind(scene.widthProperty());

            root.getChildren().add(borderPane);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
