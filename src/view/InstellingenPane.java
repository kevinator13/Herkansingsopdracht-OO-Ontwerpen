package view;

import db.Savable;
import db.Savable2;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class InstellingenPane extends GridPane {
    private ObservableList<Savable2> artikelList;
    private ObservableList<Korting> kortings;
    private TextField titleField;
    private Button btnOK;
    private Button submitButton;
    private ArrayList<String> list;
    private Properties properties;
    private ToggleGroup statementGroup;
    private RadioButton answer;
    private Label questionField;
    private Label kortingopslaan;

    private ComboBox comboBox;
    private ComboBox comboBox2;

    public InstellingenPane(ObservableList<Savable2> artikels, ObservableList<Korting> kortings) throws IOException {
        artikelList = artikels;
        this.kortings = kortings;
        list = new ArrayList<>();
        list.add("TXT");
        list.add("Excel");
        questionField= new Label();
        questionField.setText("");
        add(questionField,1,7);

        statementGroup = new ToggleGroup();
        for(int i = 0; i < list.size(); i++)
        {
            answer = new RadioButton(list.get(i));
            answer.setUserData(list.get(i));
            answer.setToggleGroup(statementGroup);
            add(answer,0,i,1,1);
        }
        submitButton = new Button("Submit");
        add(submitButton,0,6,1,1);
        setSaveAction(new saveEvaluation());




        //----------------------------------------------------------------------
        this.add(new Label("Title:"), 0, 8, 1, 1);
        titleField = new TextField();
        this.add(titleField, 1, 8, 1, 1);
        //----------------------------------------------------------------------

        comboBox = new ComboBox();
        comboBox.getItems().addAll("Drempel", "Groep", "Duurste");
        comboBox.setValue("Drempel");
        this.add(comboBox, 1, 9, 1, 1);


        comboBox2 = new ComboBox();
        comboBox2.getItems().addAll("Procent", "Geld");
        comboBox2.setValue("Procent");
        this.add(comboBox2, 1, 10, 1, 1);

        btnOK = new Button("Save");
        btnOK.isDefaultButton();
        add(btnOK, 1, 11, 1, 1);
        setKortingAction(new KortingEvaluation());
        kortingopslaan= new Label();
        kortingopslaan.setText("");
        add(kortingopslaan,1,12);


    }
    public void setSaveAction(EventHandler<ActionEvent> saveAction) {
        submitButton.setOnAction(saveAction);
    }
    public void setKortingAction(EventHandler<ActionEvent> saveAction) {
        btnOK.setOnAction(saveAction);
    }



    private class saveEvaluation implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String selection;
            Object selectedRadioButton = statementGroup.getSelectedToggle().getUserData();
            selection = (String) selectedRadioButton;

            questionField.setText("You will now save to: " + selection);

            write(selection.toLowerCase());
        }
    }

    private void write(String selection)
    {
        try
        {
            File file;
            properties = new Properties();


                properties.setProperty("save.mode", selection);
                file = new File("resources/db/save.properties");


            OutputStream out = new FileOutputStream(file);
            properties.store(out,"Properties");
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
    }



private class KortingEvaluation implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        Korting korting = null;
        String soort = (String) comboBox.getValue();
        String soort2 = (String) comboBox2.getValue();
if(titleField.getText().isEmpty()){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText("ERROR");
    alert.setContentText("niet lege korting");
    alert.showAndWait();
}
        else if (soort2.equals("Procent") && !titleField.getText().matches("\\d{0,2}([\\.]\\d{0,2})?")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ERROR");
            alert.setContentText("niet bestaande procent");
            alert.showAndWait();
        } else if (soort2.equals("Geld") && !titleField.getText().matches("\\d{0,7}([\\.]\\d{0,2})?")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ERROR");
            alert.setContentText("niet bestaande geld");
            alert.showAndWait();
        } else{
        if (soort.equals("Drempel")){
                korting = new DrempelKorting(Double.parseDouble(titleField.getText()), artikelList, soort2);
        }
        else if (soort.equals("Groep")){
            korting=new GroepsKorting(Double.parseDouble(titleField.getText()), artikelList, soort2);
        }else if (soort.equals("Duurste")){
            korting= new DuursteKorting(Double.parseDouble(titleField.getText()), artikelList, soort2);
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ERROR");
            alert.setContentText("niet bestaande code");
            alert.showAndWait();
        }
        if (korting != null){
            kortings.add(korting);
            kortingopslaan.setText("You will now save to: " + titleField.getText());
        }
        }



        }


    }
}