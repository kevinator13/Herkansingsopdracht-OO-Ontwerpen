package model;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Observable;

public class Klant extends Observable {


    public Klant() { }

    public void setAfsluiten()
    {
        setChanged();
        notifyObservers();
    }


}
