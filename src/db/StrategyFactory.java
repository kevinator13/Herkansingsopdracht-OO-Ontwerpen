package db;


import javafx.collections.ObservableList;

public class StrategyFactory {

    private static StrategyFactory strategyFactory = new StrategyFactory();

    public DBStrategy strategy;

    // private constructor restricted to this class itself
    private StrategyFactory() { }

    // static method to create instance of Singleton class
    public static StrategyFactory getInstance()
    {
        return strategyFactory;
    }

    public DBStrategy getStrategy(String soort, ObservableList<Savable> list) {
        switch (soort)
        {
            case "txt": strategy = new ArtikelTXT(list); break;
            case "excel": strategy = new ArtikelExcel(list); break;
            default: strategy = new ArtikelTXT(list); break;
        }
        return strategy;
    }
}
