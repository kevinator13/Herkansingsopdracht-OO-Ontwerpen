package view;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Verkoop;

public class LogPane extends GridPane {
    private TableView table;
    private Controller controller;


    public LogPane(Controller controller) {
        this.controller = controller;

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        this.add(new Label("log:"), 0, 0, 1, 1);
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        //----------------------------------------------------------------------
        TableColumn datumcol = new TableColumn<>("Datum");
        datumcol.setCellValueFactory(new PropertyValueFactory("datum"));
        datumcol.setMinWidth(150);
        table.getColumns().add(datumcol);
        //----------------------------------------------------------------------
        TableColumn tijdcol = new TableColumn<>("Time");
        tijdcol.setCellValueFactory(new PropertyValueFactory("time"));
        tijdcol.setMinWidth(150);
        table.getColumns().add(tijdcol);
        //----------------------------------------------------------------------
        TableColumn totaalCol = new TableColumn<>("Totaal");
        totaalCol.setCellValueFactory(new PropertyValueFactory("totaal"));
        totaalCol.setMinWidth(100);
        table.getColumns().add(totaalCol);
        //----------------------------------------------------------------------
        TableColumn kortingCol = new TableColumn<>("Korting");
        kortingCol.setCellValueFactory(new PropertyValueFactory("korting"));
        kortingCol.setMinWidth(100);
        table.getColumns().add(kortingCol);
        //----------------------------------------------------------------------
        TableColumn teBetalenCol = new TableColumn<>("TeBetalen");
        teBetalenCol.setCellValueFactory(new PropertyValueFactory("tebetalen"));
        teBetalenCol.setMinWidth(100);
        table.getColumns().add(teBetalenCol);
        //----------------------------------------------------------------------


        this.add(table, 0, 1, 2, 6);
        //----------------------------------------------------------------------

        table.setItems(this.controller.getVerkoops());
    }
}
