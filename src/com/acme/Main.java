package com.acme;

import model.Currency;
import model.Figure;
import repositories.CurrencyRepository;
import service.ExchangeService;
import service.InventoryService;

public class Main {

    public static void main(String[] args) {
        InventoryService inventoryService = InventoryService.getInstance();
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();

        Currency RON = currencyRepository.findCurrencyByCode("RON").get();
        Currency USD = currencyRepository.findCurrencyByCode("USD").get();
        Currency EUR = currencyRepository.findCurrencyByCode("EUR").get();

        inventoryService.depositIntoInventory(new Figure(RON, 100));
        inventoryService.depositIntoInventory(new Figure(USD, 1000));
        inventoryService.depositIntoInventory(new Figure(EUR, 310.75));

        System.out.println(inventoryService.getInventoryFigures());

        System.out.println(inventoryService.getInventoryFigure(RON));

        inventoryService.withdrawFromInventory(new Figure(RON, 50));
        System.out.println(inventoryService.getInventoryFigure(RON));

        ExchangeService exchangeService = ExchangeService.getInstance();
        System.out.println(exchangeService.viewBuy(new Figure(EUR, 100)));
        System.out.println(exchangeService.viewSell(new Figure(EUR, 100)));
        System.out.println(exchangeService.viewExchange(new Figure(RON, 100), EUR));
    }
}
