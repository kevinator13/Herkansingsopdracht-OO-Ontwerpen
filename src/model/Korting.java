package model;

import db.Savable2;
import javafx.collections.ObservableList;

public interface Korting {
    public double kortingEuro(ObservableList<Savable2> shoplist);
}
