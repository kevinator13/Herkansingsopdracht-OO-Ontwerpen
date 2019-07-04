package view;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.Artikel;
import model.Artikel2;
import model.Korting;

public class KlantMainPane extends BorderPane {
    public KlantMainPane(ObservableList<Savable2> shoppingcar, ObservableList<Korting> kortings) {
        KlantPane klantPane = new KlantPane(shoppingcar, kortings);
        TabPane tabPane = new TabPane();
        Tab kassaTab = new Tab("Kassa", klantPane);


        tabPane.getTabs().add(kassaTab);

        this.setCenter(tabPane);
    }
}
