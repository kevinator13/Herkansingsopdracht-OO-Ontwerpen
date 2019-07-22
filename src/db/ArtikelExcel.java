package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Artikel;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class ArtikelExcel extends ExcelDBStrategy {
    File file;
    ObservableList<Savable> artikelList = FXCollections.observableArrayList(new ArrayList<>());

    public void setArtikelList(ObservableList<Savable> artikelList) {
        this.artikelList = artikelList;
    }

    public ArtikelExcel(ObservableList<Savable> list)
    {
        this.file = new File("resources/db/artikel.xls");
        this.setArtikelList(list);
    }

    @Override
    public String getFile() {
        return "resources/db/artikel.xls";
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
    public Savable convertStringToObject(ArrayList<String> velden) {
        if(velden.size()==5)
        {
            Artikel object = new Artikel(velden.get(0), velden.get(1), velden.get(2), velden.get(3), velden.get(4));

            Savable object2 = object;

            return (Artikel)object2;
        }
        else
        {
            return null;

        }

    }

    @Override
    public String getWriteFile() {
        return "resources/db/artikel.xls";
    }

}
