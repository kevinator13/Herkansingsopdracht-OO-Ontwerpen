package view;

import controller.Controller;
import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Artikel;
import model.Artikel2;
import model.Klant;
import model.Korting;

public class KlantView {



    public KlantView(Controller controller, Klant klant){

        Stage stage = new Stage();
        stage.setTitle("KLANT VIEW");
        stage.setResizable(false);
        stage.setX(775);
        stage.setY(20);
        Group root = new Group();

        Scene scene = new Scene(root, 500, 500);
        BorderPane borderPane = new KlantMainPane(controller.getShop(), controller.getKortings(), klant);
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        root.getChildren().add(borderPane);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}

