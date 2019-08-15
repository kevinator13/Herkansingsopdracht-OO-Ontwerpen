package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Artikel;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class ArtikelTXT extends TXTDBStrategy {

    private ObservableList<Savable> artikelList = FXCollections.observableArrayList(new ArrayList<>());

    public void setArtikelList(ObservableList<Savable> artikelList) {
        this.artikelList = artikelList;
    }

    public ArtikelTXT(ObservableList<Savable> list)
    {
        File file = new File(getFile());
        this.setArtikelList(list);
    }

    @Override
    public String getFile() {
        return "resources/db/artikel.txt";
    }

    @Override
    public ObservableList<Savable> getObjectsToWrite() {
        this.artikelList.sort(new Comparator<Savable>() {
            @Override
            public int compare(Savable o1, Savable o2) {
                if (((Artikel)o1).getCode() == ((Artikel)o2).getCode()){
                    return 0;
                } else if (((Artikel)o1).getCode() > ((Artikel)o2).getCode()){
                    return 1;
                } else {
                    return -1;
                }

            }
        });
        return artikelList;
    }

    @Override
    public Savable convertStringToObject(String[] velden) {
        if(velden.length==5)
        {

            return new Artikel(velden[0], velden[1], velden[2], velden[3], velden[4]);

        }
        else
        {
            return null;

        }

    }

    @Override
    public String getWriteFile() {
        return "resources/db/artikel.txt";
    }
}
