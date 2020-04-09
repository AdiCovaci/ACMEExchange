package com.acme;

import model.Commodity;
import model.FiatCurrency;
import model.Figure;
import repositories.CurrencyRepository;
import service.ExchangeService;
import service.InventoryService;

public class Main {

    public static void main(String[] args) {
        InventoryService inventoryService = InventoryService.getInstance();
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();

        FiatCurrency RON = (FiatCurrency) currencyRepository.findCurrencyByCode("RON").get();
        FiatCurrency USD = (FiatCurrency) currencyRepository.findCurrencyByCode("USD").get();
        FiatCurrency EUR = (FiatCurrency) currencyRepository.findCurrencyByCode("EUR").get();
        Commodity Gold = (Commodity) currencyRepository.findCurrencyByCode("XAU").get();

        inventoryService.depositIntoInventory(new Figure(RON, 50000));
        inventoryService.depositIntoInventory(new Figure(USD, 1000));
        inventoryService.depositIntoInventory(new Figure(EUR, 310.75));

        System.out.println(inventoryService.getInventoryFigures());

        System.out.println(inventoryService.getInventoryFigure(RON));

        inventoryService.withdrawFromInventory(new Figure(RON, 50));
        System.out.println(inventoryService.getInventoryFigure(RON));

        Figure initialFigure = inventoryService.reportTotalInventoryValue();
        System.out.println(initialFigure);

        ExchangeService exchangeService = ExchangeService.getInstance();
        System.out.println(exchangeService.viewBuy(new Figure(EUR, 100)));
        System.out.println(exchangeService.viewSell(new Figure(EUR, 100)));
        System.out.println(exchangeService.viewExchange(new Figure(RON, 100), EUR));

        exchangeService.buy(new Figure(EUR, 100));
        System.out.println(inventoryService.getInventoryFigures());

        exchangeService.buy(new Figure(Gold, 100));
        System.out.println(inventoryService.getInventoryFigures());

        exchangeService.sell(new Figure(EUR, 100));
        System.out.println(inventoryService.getInventoryFigures());

        exchangeService.exchange(new Figure(RON, 100), EUR);
        System.out.println(inventoryService.getInventoryFigures());

        System.out.println(currencyRepository.getCurrencies());

        Figure finalFigure = inventoryService.reportTotalInventoryValue();
        System.out.println(finalFigure);

        System.out.print("Profit: ");
        System.out.println(finalFigure.subtract(initialFigure));
    }
}
