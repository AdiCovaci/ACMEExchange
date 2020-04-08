package service;

import model.Currency;
import model.Figure;
import model.Inventory;

import java.util.HashMap;

public class InventoryService {
    private Inventory inventory;

    public InventoryService() {
        inventory = Inventory.getInstance();
    }

    public void depositIntoInventory(Figure figure) {
        inventory.deposit(figure);
    }

    public void withdrawFromInventory(Figure figure) {
        inventory.withdraw(figure);
    }

    public HashMap<Currency, Figure> getInventoryFigures() {
        return inventory.getFigures();
    }

    public Figure getInventoryFigure(Currency currency) {
        return inventory.getFigure(currency);
    }

    public static InventoryService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static InventoryService INSTANCE = new InventoryService();
    }
}
