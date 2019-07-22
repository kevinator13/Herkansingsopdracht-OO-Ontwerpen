package model;


import javafx.scene.control.Alert;

import java.util.ArrayList;

public class KortingFactory {


    public Korting createKorting(KortingSoort soort, ArrayList<String> list) {
        Korting korting = null;

        Double geld = Double.parseDouble(list.get(0));
        if(soort.equals(KortingSoort.DREMPEL)){
            if (list.get(2).isEmpty() || !list.get(2).matches("\\d{0,7}([\\.]\\d{0,2})?")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("ERROR");
                alert.setContentText("drempel moet geld zijn");
                alert.showAndWait();
            } else {
                korting = new DrempelKorting(geld, list.get(1), list.get(2));
            }
        }
        else if (soort.equals(KortingSoort.GROEP)){
            korting = new GroepsKorting(geld, list.get(1), list.get(2));
        }
        else if (soort.equals(KortingSoort.DUURSTE)){
            korting = new DuursteKorting(geld,list.get(1));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ERROR");
            alert.setContentText("niet bestaande code");
            alert.showAndWait();
        }
        return korting;
    }
}



