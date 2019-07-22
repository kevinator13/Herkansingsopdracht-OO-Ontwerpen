package model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesRekening {
    private File file = new File("resources/db/rekening.properties");
    private Properties properties;
    private HashMap<String, String> maptrue;
    private HashMap<String, String> maptext;


    public void writePropertiesRekening(HashMap<String, String> listtext){
        try
        {
            HashMap<String, String> map1 = new HashMap<>();
            HashMap<String, String> map2 = new HashMap<>();

            this.properties = new Properties();
            for (Map.Entry<String, String> entry:listtext.entrySet()){
                this.properties.setProperty(entry.getKey(), entry.getValue());
                if (maptrue.keySet().contains(entry.getKey() )){
                    map1.put(entry.getKey() , entry.getValue());
                } else if (maptext.keySet().contains(entry.getKey() )){
                    map2.put(entry.getKey() , entry.getValue());
                }
            }
            this.maptrue = map1;
            this.maptext = map2;




            OutputStream out = new FileOutputStream(this.file);
            this.properties.store(out,"Properties");
        }
        catch (Exception e ) {
            e.printStackTrace();
        }

    }



    public void readPropertyRekening() {

        InputStream is;
        this.properties =  new Properties();
        try {

            is = this.getClass().getClassLoader().getResourceAsStream("resources/db/rekening.properties");
            try {
                is = new FileInputStream(this.file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch ( Exception e ) { is = null; }
        try {
            if ( is == null ) {
                is = getClass().getClassLoader().getResourceAsStream("resources/db/rekening.properties");
            }
            this.properties.load( is );
        }
        catch ( Exception e ) { }
        //--------------------------------------------------
        String algemene = properties.getProperty("algemene.mode", "false");
        String datum = properties.getProperty("datum.mode", "false");
        String afsluit = properties.getProperty("afsluit.mode", "false");
        String zonder= properties.getProperty("zonderKorting.mode", "false");
        String btw = properties.getProperty("btw.mode", "false");
        this.maptrue = new HashMap<>();
        this.maptrue.put("algemene.mode", algemene);
        this.maptrue.put("datum.mode", datum);
        //***********************************************************************
        this.maptrue.put("afsluit.mode", afsluit);
        this.maptrue.put("zonderKorting.mode", zonder);
        this.maptrue.put("btw.mode", btw);

        String algemenetext = properties.getProperty("algemeneBoodschap.mode", "");
        String afsluittext = properties.getProperty("afsluitBoodschap.mode", "");
        this.maptext = new HashMap<>();

        this.maptext.put("algemeneBoodschap.mode", algemenetext);
        this.maptext.put("afsluitBoodschap.mode", afsluittext);

    }
    public ArrayList<String> getTextRekening(){
        ArrayList<String> list = new ArrayList();
        list.add("algemene");
        list.add("datum");
        list.add("zonderKorting");
        list.add("btw");
        list.add("afsluit");
        return list;
    }

    public HashMap<String, String> getMaptrue() {
        return maptrue;
    }

    public HashMap<String, String> getMaptext() {
        return maptext;
    }
}
