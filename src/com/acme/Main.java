package com.acme;

import model.Currency;
import model.Figure;
import service.InventoryService;

public class Main {

    public static void main(String[] args) {
        InventoryService inventoryService = InventoryService.getInstance();

        Currency RON = new Currency("Romanian Leu", "RON");
        Currency USD = new Currency("United States Dollar", "USD");
        Currency EUR = new Currency("European Euro", "EUR");

        inventoryService.depositIntoInventory(new Figure(RON, 100));
        inventoryService.depositIntoInventory(new Figure(USD, 1000));
        inventoryService.depositIntoInventory(new Figure(EUR, 310.75));

        System.out.println(inventoryService.getInventoryFigures());

        System.out.println(inventoryService.getInventoryFigure(RON));

        inventoryService.withdrawFromInventory(new Figure(RON, 50));
        System.out.println(inventoryService.getInventoryFigure(RON));
    }
}
