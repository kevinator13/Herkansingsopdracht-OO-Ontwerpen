package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;
import sun.plugin2.main.server.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ExcelDBStrategy implements DBStrategy {
    ExcelPlugin plugin = new ExcelPlugin();
    File file = new File(getWriteFile());

    private ObservableList<Savable> savables = FXCollections.observableArrayList(new ArrayList<>());
    private InputStream inputStream;



    @Override
    public void write(){
        try {
            plugin.write(this.file, this.turnObjectsIntoString());
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void read() {
        inputStream = getClass().getClassLoader().getResourceAsStream(getFile());
        file = new File(getWriteFile());
        if(file.exists() && !file.isDirectory()){
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }



        ArrayList<ArrayList<String>> list = new ArrayList<>();
        try {
            list = plugin.read(this.file);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ArrayList<String> string:list) {
            Savable savable = convertStringToObject(string);
            this.savables.add(savable);
        }
    }


   public ArrayList<ArrayList<String>> turnObjectsIntoString(){
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        for (Savable savable:getObjectsToWrite()){
            Artikel artikel = (Artikel)savable;
            ArrayList<String> list = new ArrayList<>();
            list.add("" + artikel.getCode());
            list.add(artikel.getOmschrijving());
            list.add(artikel.getArtikelgroep());
            list.add(""+artikel.getVerkoopprijs());
            list.add(""+artikel.getVoorraad());
            lists.add(list);
        }

        return lists;
   }

    @Override
    public ObservableList<Savable> getReadObjects(){

        return savables;
    }

    public abstract String getFile();

    public abstract List<Savable> getObjectsToWrite();

    public abstract Savable convertStringToObject(ArrayList<String> list);

    public abstract String getWriteFile();
}
