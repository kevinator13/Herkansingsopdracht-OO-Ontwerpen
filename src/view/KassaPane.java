package view;

import controller.DBContext;
import db.Savable;
import db.Savable2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Artikel;
import model.Artikel2;
import model.Korting;
import model.Rekening;

import java.util.ArrayList;
import java.util.List;

public class KassaPane extends GridPane {
    private Button btnOK, btnCancel;
    private TextField titleField, descriptionField;

    private ObservableList<Savable> artikelList;
    private ObservableList<Savable2> shopList;
    private ObservableList<Korting> kortings;
    private TableView table;
    private Rekening rekening;
    private Label totaleprijs;
    private Label kortingprijs;
    private Label totalekortingprijs;


    public KassaPane(ObservableList<Savable> artikels, ObservableList<Savable2> shop, ObservableList<Korting> kortings) {
        rekening = new Rekening();
        artikelList= artikels;
        shopList= shop;
        this.kortings = kortings;

        this.setPrefHeight(150);
        this.setPrefWidth(300);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        //----------------------------------------------------------------------
        this.add(new Label("Title:"), 0, 0, 1, 1);
        titleField = new TextField();
        this.add(titleField, 1, 0, 1, 1);
         //----------------------------------------------------------------------

        btnOK = new Button("Save");
        btnOK.isDefaultButton();
        this.add(btnOK, 1, 2, 1, 1);
        setSaveAction(new AddCategory());
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        //----------------------------------------------------------------------
        TableColumn nameCol = new TableColumn<>("Omschrijving");
        nameCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        table.getColumns().add(nameCol);
        //----------------------------------------------------------------------
        TableColumn verkoopprijsCol = new TableColumn<>("Verkoopprijs");
        verkoopprijsCol.setCellValueFactory(new PropertyValueFactory("verkoopprijs"));
        table.getColumns().add(verkoopprijsCol);
        //----------------------------------------------------------------------
        this.add(table, 0, 9, 2, 6);
        setEditAction(new EditCategory());
        //----------------------------------------------------------------------
        for (Artikel artikel: rekening.getList()){
            table.getItems().add(artikel);
        }
        String prijs = "total " + rekening.getTotal();
        totaleprijs = new Label(prijs);
        this.add(totaleprijs, 0, 5, 1, 1 );

        kortingprijs = new Label();
        this.add(kortingprijs, 1, 5, 1, 1 );
        totalekortingprijs = new Label();
        this.add(totalekortingprijs, 2, 5, 1, 1 );


        btnCancel = new Button("AFSLUIT");
        this.add(btnCancel, 0, 2, 1, 1);
        setAfsluitenAction(new Afsluiten());
        //----------------------------------------------------------------------

    }
    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }
    public void setSaveAction(EventHandler<ActionEvent> saveAction) {
        btnOK.setOnAction(saveAction);
    }
    public void setAfsluitenAction(EventHandler<ActionEvent> saveAction) {
        btnCancel.setOnAction(saveAction);
    }
    public class AddCategory implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            Artikel toAdd = null;

            for(Savable artikel : artikelList)
            {
               if (Integer.toString(((Artikel)artikel).getCode()).equals(titleField.getText())){
                    toAdd = (Artikel)artikel;
               }

            }
            if (toAdd == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("ERROR");
                alert.setContentText("niet bestaande code");
                alert.showAndWait();
            }
            else{
                rekening.addArtikel(toAdd);
                table.getItems().add(toAdd);

                double total = 0.0;
                for (int i = 0; i < table.getItems().size(); i++) {
                    total += ((Artikel)table.getItems().get(i)).getVerkoopprijs();
                }
                String newLabel = "total " + total;

                totaleprijs.setText(newLabel);
                Boolean bool = true;
                String om = toAdd.getOmschrijving();
                for (Savable2 artikel2: shopList){
                    if (((Artikel2)artikel2).getOmschrijving().equals(om)){
                        ((Artikel2)artikel2).addMore();
                        System.out.println(((Artikel2)artikel2).getAantal());
                        bool = false;
                    }

                }
                if (bool){
                    shopList.add(new Artikel2(toAdd));
                }

            }


        }
    }
    private class EditCategory implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    TableView.TableViewSelectionModel<Artikel> tableView = table.getSelectionModel();
                    int index = tableView.getSelectedIndex();
                    Artikel artikel = tableView.getSelectedItem();
                    table.getItems().remove(artikel);

                    double total = 0.0;
                    for (int i = 0; i < table.getItems().size(); i++) {
                        total += ((Artikel)table.getItems().get(i)).getVerkoopprijs();
                    }
                    String newLabel = "total: " + total;

                    totaleprijs.setText(newLabel);
                }
            }
        }
    }

    public class Afsluiten implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            Double total = 0.0;
            for (int i = 0; i < table.getItems().size(); i++) {
                total += ((Artikel)table.getItems().get(i)).getVerkoopprijs();
            }

            Double kortingbedrag = 0.0;
            for(Korting korting : kortings)
            {
                kortingbedrag += korting.kortingEuro();
            }
            total -= kortingbedrag;
            String kortingtext = "Korting: " + kortingbedrag;
            String totalekortingtext = "Totaal met Korting: " + total;
            kortingprijs.setText(kortingtext);
            totalekortingprijs.setText(totalekortingtext);
            }


        }
    }

