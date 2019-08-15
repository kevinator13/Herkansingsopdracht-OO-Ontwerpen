package model;


import java.util.Observable;

// is om scheiding tussen kassaPane en klantPane te hebben

public class Klant extends Observable {


    public Klant() { }

    public void setAfsluiten()
    {
        setChanged();
        notifyObservers();
    }


}
