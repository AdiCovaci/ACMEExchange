package model;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Currency, Figure> figures = new HashMap<>();

    private Inventory() {

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
        Figure inventoryFigure = getFigure(figure.getCurrency());

        if (hasCurrency(figure.getCurrency()))
            setFigure(inventoryFigure.add(figure));
        else
            setFigure(figure);
    }

    public void withdraw(Figure figure) throws InsufficientFundsException {
        Figure inventoryFigure = getFigure(figure.getCurrency());

        if (inventoryFigure.subtract(figure).isNegative())
            throw new InsufficientFundsException();

        if (hasCurrency(figure.getCurrency()))
            setFigure(inventoryFigure.subtract(figure));
    }

    public Map<Currency, Figure> getFigures() {
        return figures;
    }

    public static Inventory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static Inventory INSTANCE = new Inventory();
    }

    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException() {
            super("Not enough funds in inventory for the current operation");
        }
    }
}
