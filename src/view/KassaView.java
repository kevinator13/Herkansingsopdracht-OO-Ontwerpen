package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KassaView {
    private Stage stage;

    public KassaView(Stage stage, Pane kassaPane, Pane artikelOverviewPane, Pane instellingenPane, Pane logPane){
        this.stage = stage;
        stage.setTitle("KASSA VIEW");
        stage.setResizable(false);
        stage.setX(20);
        stage.setY(20);
        Group root = new Group();
        Scene scene = new Scene(root, 750, 500);
        BorderPane borderPane = new KassaMainPane(kassaPane, artikelOverviewPane, instellingenPane, logPane);
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        root.getChildren().add(borderPane);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}

