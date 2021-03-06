package view;

import controller.Controller;
import db.Savable;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;



public class ArtikelOverviewPane extends GridPane {
    private TableView table;
    private Controller controller;


    public ArtikelOverviewPane(Controller controller) {
        this.controller = controller;
        ObservableList<Savable> artikelList = this.controller.getArtikels();
        artikelList.addListener(new ListChangeListener<Savable>() {
            @Override
            public void onChanged(Change<? extends Savable> changed) {
                table.refresh();
            }
        });



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
        omschrijvingCol.setMinWidth(200);
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

        this.add(table, 0, 1, 2, 6);
        //----------------------------------------------------------------------


        SortedList<Savable> sortedData = new SortedList<>(artikelList);


        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);


        table.getSortOrder().addAll(omschrijvingCol);




    }
}
