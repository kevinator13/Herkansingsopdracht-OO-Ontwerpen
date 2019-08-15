package view;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.*;

import java.io.*;
import java.util.*;

public class InstellingenPane extends GridPane {
    private Controller controller;
    private HashMap<String, String> maptrue; //  weg
    private HashMap<String, String> maptext; //  weg
    private ObservableList<Korting> kortings; //  weg

    private TextField kortingField;
    private TextField extraKortingField;
    private Button btnOK;
    private Button submitButton;

    private Properties properties; // Misschien beter in Property klasse
    private ToggleGroup statementGroup;
    private RadioButton answer; //  weg mischien
    private Label questionField;
    private Label kortingopslaan;
    private Label rekeningopslaan;

    private ComboBox comboBox;
    private ComboBox comboBox2;

    private List<RadioButton> myButtonGroup;
    private Button btnSaveRekening;
    private TextField algemeneBoodschap;
    private TextField afsluitBoodschap;

    public InstellingenPane(Controller controller) throws IOException {
        this.controller = controller;
        this.maptrue = this.controller.getMaptrue();
        this.maptext = this.controller.getMaptext();
        this.kortings =this.controller.getKortings();

        ArrayList<String> list = new ArrayList<>();
        list.add("TXT");
        list.add("EXCEL");
        String prop = controller.getOutputProperty().toUpperCase();
        questionField= new Label();
        questionField.setText("");
        add(questionField,1,7);

        statementGroup = new ToggleGroup();
        for(int i = 0; i < list.size(); i++)
        {
            answer = new RadioButton(list.get(i));
            answer.setUserData(list.get(i));
            answer.setToggleGroup(statementGroup);
            if (prop.equals(list.get(i))){
                answer.setSelected(true);
            }
            add(answer,0,i,1,1);

        }
        submitButton = new Button("Submit");
        add(submitButton,0,6,1,1);
        setSaveAction(new saveEvaluation());

        this.add(new Label("-----------------------------"), 0,8,1,1);




        //----------------------------------------------------------------------
        this.add(new Label("Korting:"), 0, 9, 1, 1);
        kortingField = new TextField();
        this.add(kortingField, 1, 9, 1, 1);
        //----------------------------------------------------------------------

        this.add(new Label("Bepaalde Drempel of Groep:"), 2, 9, 1, 1);
        extraKortingField = new TextField();
        this.add(extraKortingField, 3, 9, 1, 1);
        //----------------------------------------------------------------------

        comboBox = new ComboBox();
        comboBox.getItems().addAll(KortingSoort.values());
        comboBox.setValue(KortingSoort.DREMPEL);
        this.add(comboBox, 1, 10, 1, 1);


        comboBox2 = new ComboBox();
        comboBox2.getItems().addAll("Procent", "Geld");
        comboBox2.setValue("Procent");
        this.add(comboBox2, 1, 11, 1, 1);

        btnOK = new Button("Save");
        btnOK.isDefaultButton();
        add(btnOK, 1, 12, 1, 1);
        setKortingAction(new KortingEvaluation());
        kortingopslaan= new Label();
        kortingopslaan.setText("");
        add(kortingopslaan,1,13, 3, 1);
        this.add(new Label("-----------------------------"), 0,14,1,1);






        this.myButtonGroup = new ArrayList<>();

        ArrayList<String> listtext = this.controller.getTextRekening();
        for (int i = 0; i<listtext.size(); i++)
        {

            RadioButton button = new RadioButton(listtext.get(i));
            button.setUserData(listtext.get(i));
            if (maptrue.get(listtext.get(i) + ".mode").equals("true")){
                button.setSelected(true);
            }
            else {
                button.setSelected(false);
            }
            myButtonGroup.add(button);
            add(button, 0, 15+i, 1, 1);


        }
        btnSaveRekening = new Button("SaveRekening");
        btnSaveRekening.isDefaultButton();
        add(btnSaveRekening, 0, 20, 1, 1);
        setRekeningAction(new RekeningEvaluation());

        this.add(new Label("AlgemeneBoodchap:"), 1, 15, 1, 1);
        algemeneBoodschap = new TextField();
        algemeneBoodschap.setText(this.maptext.get("algemeneBoodschap.mode"));
        this.add(algemeneBoodschap, 2, 15, 1, 1);


        this.add(new Label("Afsluitboodschap:"), 1, 19, 1, 1);
        afsluitBoodschap = new TextField();
        afsluitBoodschap.setText(this.maptext.get("afsluitBoodschap.mode"));
        this.add(afsluitBoodschap, 2, 19, 1, 1);

        rekeningopslaan= new Label();
        rekeningopslaan.setText("");
        add(rekeningopslaan,0,21);








    }
    public void setSaveAction(EventHandler<ActionEvent> saveAction) {
        submitButton.setOnAction(saveAction);
    }
    public void setKortingAction(EventHandler<ActionEvent> saveAction) {
        btnOK.setOnAction(saveAction);
    }
    public void setRekeningAction(EventHandler<ActionEvent> saveAction) {
        btnSaveRekening.setOnAction(saveAction);
    }

    //  waarom private klasse vanonder
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
        KortingSoort soort = (KortingSoort)comboBox.getValue();


        String soort2 = (String) comboBox2.getValue();
        if(kortingField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ERROR");
            alert.setContentText("niet lege korting");
            alert.showAndWait();
        }
        else if (soort2.equals("Procent") && !kortingField.getText().matches("\\d{0,2}([\\.]\\d{0,2})?")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ERROR");
            alert.setContentText("niet bestaande procent");
            alert.showAndWait();
        } else if (soort2.equals("Geld") && !kortingField.getText().matches("\\d{0,7}([\\.]\\d{0,2})?")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ERROR");
            alert.setContentText("niet bestaande geld");
            alert.showAndWait();
        } else{
            KortingFactory kortingFactory = new KortingFactory();
            ArrayList<String>textlist = new ArrayList<>();
            textlist.add(kortingField.getText());
            textlist.add(soort2);
            textlist.add(extraKortingField.getText());
            korting = kortingFactory.createKorting(soort, textlist);

        if (korting != null){
            kortings.add(korting);
            kortingopslaan.setText("korting is opgeslagen: " + kortingField.getText() + ", " + soort2 + ", " + soort + ", extra variable = " + extraKortingField.getText());
        }
        }



        }


    }


    private class RekeningEvaluation implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            HashMap<String, String> listtext = new HashMap<>();

            String afsluitBoodschapText = afsluitBoodschap.getText();
            if (afsluitBoodschap.getText() == null){
                afsluitBoodschapText = "";
            }
            String algemeneBoodschapText = algemeneBoodschap.getText();
            if (algemeneBoodschap.getText() == null){
                algemeneBoodschapText = "";
            }
            for (RadioButton button: myButtonGroup){
                String regel = (String) button.getUserData();
                if (button.isSelected()){
                    listtext.put(regel + ".mode", "true");
                } else {
                    listtext.put(regel + ".mode", "false");
                }
            }
            listtext.put("algemeneBoodschap.mode", algemeneBoodschapText);
            listtext.put("afsluitBoodschap.mode", afsluitBoodschapText);

            controller.writePropertiesRekening(listtext);
            rekeningopslaan.setText("rekening is opgeslagen." );



        }


    }


}