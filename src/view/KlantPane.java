package view;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Artikel;
import model.Artikel2;
import model.Korting;

public class KlantPane extends GridPane {
    private TableView table;
    private ObservableList<Savable2> artikelList;

    public KlantPane(ObservableList<Savable2> artikelList, ObservableList<Korting> kortings) {
        this.artikelList = artikelList;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        this.add(new Label("Artikel:"), 0, 0, 1, 1);
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        //----------------------------------------------------------------------
        TableColumn codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory("code"));
        table.getColumns().add(codeCol);
        //----------------------------------------------------------------------
        TableColumn omschrijvingCol = new TableColumn<>("Omschrijving");
        omschrijvingCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        table.getColumns().add(omschrijvingCol);
        //----------------------------------------------------------------------
        TableColumn artikelgroepCol = new TableColumn<>("Artikelgroep");
        artikelgroepCol.setCellValueFactory(new PropertyValueFactory("artikelgroep"));
        table.getColumns().add(artikelgroepCol);
        //----------------------------------------------------------------------
        TableColumn verkoopprijsCol = new TableColumn<>("Verkoopprijs");
        verkoopprijsCol.setCellValueFactory(new PropertyValueFactory("verkoopprijs"));
        table.getColumns().add(verkoopprijsCol);
        //----------------------------------------------------------------------
        TableColumn voorraadCol = new TableColumn<>("Voorraad");
        voorraadCol.setCellValueFactory(new PropertyValueFactory("voorraad"));
        table.getColumns().add(voorraadCol);
        //----------------------------------------------------------------------
        TableColumn aantalCol = new TableColumn<>("Aantal");
        aantalCol.setCellValueFactory(new PropertyValueFactory("aantal"));
        table.getColumns().add(aantalCol);
        //----------------------------------------------------------------------

        this.add(table, 0, 1, 2, 6);
        //----------------------------------------------------------------------
        System.out.println(this.artikelList);
        table.setItems(artikelList);
    }}
