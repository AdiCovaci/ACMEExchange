package model;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private HashMap<Currency, Figure> figures;

    public Inventory() {
        figures = new HashMap<>();
    }

    public boolean hasCurrency(Currency currency) {
        return figures.containsKey(currency);
    }

    public Figure getFigure(Currency currency) {
        if (!hasCurrency(currency))
            return new Figure(currency, 0);

        return figures.get(currency);
    }

    public void setFigure(Figure figure) {
        figures.put(figure.getCurrency(), figure);
    }

    public void deposit(Figure figure) {
        if (hasCurrency(figure.getCurrency()))
            figures.get(figure.getCurrency()).add(figure);
        else
            setFigure(figure);
    }

    public void withdraw(Figure figure) {
        if (hasCurrency(figure.getCurrency()))
            figures.get(figure.getCurrency()).subtract(figure);
    }

    public HashMap<Currency, Figure> getFigures() {
        return figures;
    }

    public static Inventory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static Inventory INSTANCE = new Inventory();
    }
}
