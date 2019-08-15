package view;


import db.Savable2;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Artikel2;
import model.Klant;
import model.Korting;
import java.util.Observable;
import java.util.Observer;


import javafx.scene.layout.GridPane;

public class KlantPane extends GridPane implements Observer  {
    private TableView table;
    private ObservableList<Savable2> artikelList;
    private ObservableList<Korting> kortings;


    private Label totaleprijs;
    private double prijstotaal = 0.0;
    private Label kortingprijs;
    private Label totalekortingprijs;

    public KlantPane(ObservableList<Savable2> artikelList, ObservableList<Korting> kortings, Klant klant) {
        this.artikelList = artikelList;

        this.kortings = kortings;
        klant.addObserver(this);

        artikelList.addListener(new ListChangeListener<Savable2>() {
            @Override
            public void onChanged(Change<? extends Savable2> changed) {

                while (changed.next()){
                    if (changed.wasAdded()){

                        prijstotaal += ((Artikel2)changed.getAddedSubList().get(0)).getVerkoopprijs();

                    }
                    else if (changed.wasRemoved()){
                        prijstotaal -= ((Artikel2)changed.getRemoved().get(0)).getVerkoopprijs();

                    }

                }
                table.refresh();
                String prijs = "total: " + prijstotaal;
                totaleprijs.setText(prijs);



            }
        });


        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        this.add(new Label("Artikel:"), 0, 0, 1, 1);
        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        //----------------------------------------------------------------------

        TableColumn omschrijvingCol = new TableColumn<>("Omschrijving");
        omschrijvingCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        omschrijvingCol.setMinWidth(150);
        table.getColumns().add(omschrijvingCol);
        //----------------------------------------------------------------------

        TableColumn verkoopprijsCol = new TableColumn<>("Verkoopprijs");
        verkoopprijsCol.setCellValueFactory(new PropertyValueFactory("verkoopprijs"));
        verkoopprijsCol.setMinWidth(150);
        table.getColumns().add(verkoopprijsCol);
        //----------------------------------------------------------------------

        TableColumn aantalCol = new TableColumn<>("Aantal");
        aantalCol.setCellValueFactory(new PropertyValueFactory("aantal"));
        aantalCol.setMinWidth(150);
        table.getColumns().add(aantalCol);
        //----------------------------------------------------------------------

        this.add(table, 0, 2, 3, 6);
        //----------------------------------------------------------------------


        table.setItems(artikelList);






        String prijs = "total: 0.0";
        totaleprijs = new Label(prijs);
        kortingprijs = new Label();
        totalekortingprijs = new Label();

        this.add(totaleprijs, 0, 1, 1, 1 );
        this.add(kortingprijs, 1, 1, 1, 1 );
        this.add(totalekortingprijs, 2, 1, 1, 1 );
    }

    @Override
    public void update(Observable o, Object arg) {
        Double kortingbedrag = 0.0;
        for(Korting korting : kortings)
        {
            kortingbedrag += korting.kortingEuro(artikelList);
        }
        String kortingprijseuro = "korting: " + kortingbedrag;
        kortingprijs.setText(kortingprijseuro);
        Double totaalkortingbedrag = prijstotaal - kortingbedrag;
        String totaalkortingprijseuro = "totaal met korting: " + totaalkortingbedrag;
        totalekortingprijs.setText(totaalkortingprijseuro);


    }
}
