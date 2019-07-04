package view;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class KassaMainPane extends BorderPane {
    public KassaMainPane(Pane kassaPane, Pane ArtikelOverviewPane, Pane instellingenPane){
        TabPane tabPane = new TabPane();
        Tab kassaTab = new Tab("Kassa", kassaPane);
        Tab artikelTab = new Tab("Artikelen", ArtikelOverviewPane);
        Tab instellingTab = new Tab("Instellingen", instellingenPane);
        Tab logTab = new Tab("Log");
        tabPane.getTabs().add(kassaTab);
        tabPane.getTabs().add(artikelTab);
        tabPane.getTabs().add(instellingTab);
        tabPane.getTabs().add(logTab);
        this.setCenter(tabPane);
    }
}

