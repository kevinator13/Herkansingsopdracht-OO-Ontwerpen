package view;

import controller.Controller;
import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class KassaPane extends GridPane {
private Controller controller;
    private ComboBox comboBox;
    private Button btnOK, btnAfsluiten, btnBetalen, btnAnuleren, btnSetOnHold;
    private TextField titleField;

    private ObservableList<Savable> artikelList;
    private ObservableList<Savable2> shopList;
    private ObservableList<Korting> kortings;


    private TableView table;
    private Rekening rekening;
    private Label totaleprijs;
    private Label kortingprijs;
    private Label totalekortingprijs;
    private Klant klant;
    private Verkoop verkoop;
    private Verkoop verkoopOnHold;
    private ObservableList<Verkoop>verkoops;


    public KassaPane(Controller controller, Klant klant) {
        this.controller = controller;

        this.artikelList= controller.getArtikels();
        this.shopList= controller.getShop();
        this.kortings = controller.getKortings();
        this.verkoops = controller.getVerkoops();
        this.klant = klant;
        this.verkoop = new Verkoop();

        this.setPrefHeight(150);
        this.setPrefWidth(300);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        //----------------------------------------------------------------------
        this.add(new Label("Code:"), 0, 0, 1, 1);
        titleField = new TextField();
        this.add(titleField, 1, 0, 1, 1);
         //----------------------------------------------------------------------

        btnOK = new Button("Scan");
        btnOK.isDefaultButton();
        this.add(btnOK, 0, 2, 1, 1);
        setSaveAction(new AddCategory());
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        //----------------------------------------------------------------------
        TableColumn nameCol = new TableColumn<>("Omschrijving");
        nameCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        nameCol.setMinWidth(200);
        table.getColumns().add(nameCol);
        //----------------------------------------------------------------------
        TableColumn verkoopprijsCol = new TableColumn<>("Verkoopprijs");
        verkoopprijsCol.setCellValueFactory(new PropertyValueFactory("verkoopprijs"));
        verkoopprijsCol.setMinWidth(200);
        table.getColumns().add(verkoopprijsCol);
        //----------------------------------------------------------------------
        this.add(table, 0, 9, 6, 6);
        setEditAction(new EditCategory());
        //----------------------------------------------------------------------

        String prijs = "total: 0.0";
        totaleprijs = new Label(prijs);
        this.add(totaleprijs, 0, 5, 1, 1 );

        kortingprijs = new Label();
        this.add(kortingprijs, 1, 5, 1, 1 );
        totalekortingprijs = new Label();
        this.add(totalekortingprijs, 2, 5, 1, 1 );


        btnAfsluiten = new Button("AFSLUIT");
        this.add(btnAfsluiten, 1, 2, 1, 1);
        setAfsluitenAction(new Afsluiten());
        //----------------------------------------------------------------------
        comboBox = new ComboBox();
        comboBox.getItems().addAll("New", "GetOnHold");
        comboBox.setValue("New");
        this.add(comboBox, 2, 2, 1, 1);


        btnBetalen = new Button("betalen");
        this.add(btnBetalen, 3, 2, 1, 1);
        setBetalenAction(new Betalen());
        //----------------------------------------------------------------------
        btnAnuleren = new Button("anuleren");
        this.add(btnAnuleren, 4, 2, 1, 1);
        setAnulerenAction(new Anuleren());
        //----------------------------------------------------------------------
        btnSetOnHold = new Button("SetOnHold");
        this.add(btnSetOnHold, 5, 2, 1, 1);
        setOnHoldAction(new SetOnHold());
        //----------------------------------------------------------------------

        this.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnOK.fire();
                event.consume();
            }});

    }
    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }
    public void setSaveAction(EventHandler<ActionEvent> saveAction) {
        btnOK.setOnAction(saveAction);
    }
    public void setAfsluitenAction(EventHandler<ActionEvent> saveAction) {
        btnAfsluiten.setOnAction(saveAction);
    }
    public void setBetalenAction(EventHandler<ActionEvent> saveAction) {
        btnBetalen.setOnAction(saveAction);
    }
    public void setAnulerenAction(EventHandler<ActionEvent> saveAction) {
        btnAnuleren.setOnAction(saveAction);
    }
    public void setOnHoldAction(EventHandler<ActionEvent> saveAction) {
        btnSetOnHold.setOnAction(saveAction);
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

                table.getItems().add(toAdd);

                double total = 0.0;
                for (int i = 0; i < table.getItems().size(); i++) {
                    total += ((Artikel)table.getItems().get(i)).getVerkoopprijs();
                }
                String newLabel = "total " + total;

                totaleprijs.setText(newLabel);
                Boolean bool = true;
                String om = toAdd.getOmschrijving();
                Artikel2 artikel21 = null;
                for (Savable2 artikel2: shopList){
                    if (((Artikel2)artikel2).getOmschrijving().equals(om)){
                        artikel21 = (Artikel2) artikel2;
                        bool = false;
                    }

                }
                if (bool){
                    shopList.add(new Artikel2(toAdd));

                }else {

                    shopList.remove(artikel21);
                    artikel21.addMore();
                    shopList.add(artikel21);

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

                    for (int i = 0; i < shopList.size(); i++){
                        if (((Artikel2)shopList.get(i)).getOmschrijving().equals(artikel.getOmschrijving())){
                            if (((Artikel2)shopList.get(i)).getAantal()>1){
                                Artikel2 artikel2 = (Artikel2)shopList.get(i);
                                shopList.remove(shopList.get(i));
                                artikel2.removeOne();
                                shopList.add(artikel2);


                            }
                            else{
                                shopList.remove(shopList.get(i));
                            }
                            i = shopList.size();
                        }
                    }



                    totaleprijs.setText(newLabel);
                }
            }
        }
    }

    public class Afsluiten implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            Double total = controller.getprijstotaal();


            Double kortingbedrag = controller.getKortingsprijs();

            Double tebetalen =  total - kortingbedrag;
            verkoop.setWaarden(total, kortingbedrag);
            String kortingtext = "Korting: " + kortingbedrag;
            String totalekortingtext = "Totaal met Korting: " + tebetalen;
            kortingprijs.setText(kortingtext);
            totalekortingprijs.setText(totalekortingtext);
            klant.setAfsluiten();

            }


        }


    public class Betalen implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {

            HashMap<String, String> maptrue = controller.getMaptrue();
            HashMap<String, String> maptext = controller.getMaptext();
            rekening = new RekeningComponent(shopList, kortings);

            if (maptrue.get("datum.mode").equals("true")){
                rekening = new RekeningDatum(rekening);
            }
            if (maptrue.get("algemene.mode").equals("true")){
                rekening = new RekeningAlgemeneBoodschap(rekening, maptext.get("algemeneBoodschap.mode"));
            }
            if (maptrue.get("zonderKorting.mode").equals("true")){
                rekening = new RekeningZonderKorting(rekening);
            }
            if (maptrue.get("btw.mode").equals("true")){
                rekening = new RekeningBTW(rekening);
            }
            if (maptrue.get("afsluit.mode").equals("true")){
                rekening = new RekeningAlgemeneAfsluitlijn(rekening, maptext.get("afsluitBoodschap.mode"));
            }

            System.out.println(rekening.rekeningFunctie());

            //--------------------------------

            ArrayList<String> error = new ArrayList<>();
            for (Savable2 artikel2: shopList){
                for (int i = 0; i<artikelList.size(); i++){
                    if (((Artikel)artikelList.get(i)).getOmschrijving().equals(((Artikel2)artikel2).getOmschrijving())){
                        if (((Artikel)artikelList.get(i)).getVoorraad()<((Artikel2)artikel2).getAantal()){
                            error.add(((Artikel)artikelList.get(i)).getOmschrijving());
                        }
                        i = artikelList.size();
                    }
                }
            }
            if (error.size() == 0){
                for (Savable2 artikel2: shopList){
                    for (int i = 0; i<artikelList.size(); i++){
                        if (((Artikel)artikelList.get(i)).getOmschrijving().equals(((Artikel2)artikel2).getOmschrijving())){
                            Savable savable = artikelList.get(i);
                            artikelList.remove(savable);
                            ((Artikel)savable).verlaagVoorraad(((Artikel2)artikel2).getAantal());
                            artikelList.add(savable);

                            i = artikelList.size();
                        }
                    }
                }
            } else{
                String errortext = "er zijn niet genoeg items van volgende artikelen: \n";
                for(String text: error){
                    errortext += text + "\n";
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("ERROR");
                alert.setContentText(errortext);
                alert.showAndWait();
            }
            //--------------------------

            verkoop.setMoment(new VerkoopBetaald(), controller.getprijstotaal(), controller.getKortingsprijs());
            verkoops.add(verkoop);
            //-----------
            String soort = (String) comboBox.getValue();
            if (soort.equals("New")){
                verkoop = new Verkoop();

                ArrayList<Savable2> list = new ArrayList<>();
                for (int i = 0 ; i<shopList.size();i++){
                    Savable2 savable2 = shopList.get(i);
                    list.add(savable2);
                }
                for (Savable2 savable2:list){
                    shopList.remove(savable2);
                }
                table.getItems().clear();
                totaleprijs.setText("total: 0.0");
            } else if(soort.equals("GetOnHold")) {
                verkoop = verkoopOnHold;
                verkoop.setState(new VerkoopNew());
                ArrayList<Savable2> list = new ArrayList<>();
                for (int i = 0 ; i<shopList.size();i++){
                    Savable2 savable2 = shopList.get(i);
                    list.add(savable2);
                }
                for (Savable2 savable2:list){
                    shopList.remove(savable2);
                }
                table.getItems().clear();
                totaleprijs.setText("total: 0.0");

                table.getItems().clear();
                for(Savable2 artikel2: verkoop.getShoplist()){
                    shopList.add(artikel2);
                }
                double newtotaal = 0.0;
                for (Savable artikel: verkoop.getList()){
                    table.getItems().add((Artikel)artikel);
                    newtotaal += ((Artikel)artikel).getVerkoopprijs();
                }
                totaleprijs.setText("total: " + newtotaal);
            }


            controller.writeArtikels();

        }


    }


    public class Anuleren implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            for (int i = 0 ; i<shopList.size();i++){
                shopList.remove(shopList.get(i));
            }
            table.getItems().clear();
            totaleprijs.setText("total: 0.0");
            verkoop = new Verkoop();

        }


    }

    private class SetOnHold implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ArrayList<Savable2> list = new ArrayList<>();
            for(Savable2 artikel2: shopList){
                list.add(artikel2);
            }
            ArrayList<Savable> list2 = new ArrayList<>();
            for(Object artikel: table.getItems()){
                list2.add((Savable) artikel);
            }
            verkoop.setShoplist(list);
            verkoop.setArtikelList(list2);
            if (verkoopOnHold != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("ERROR");
                alert.setContentText("er zit iemand anders al op hold");
                alert.showAndWait();
            } else{
                verkoopOnHold = verkoop;
                verkoopOnHold.setState(new VerkoopOnHold());
                verkoop = new Verkoop();

                for (int i = 0 ; i<shopList.size();i++){
                    shopList.remove(shopList.get(i));
                }
                table.getItems().clear();
                totaleprijs.setText("total: 0.0");
            }
        }
    }



    }

