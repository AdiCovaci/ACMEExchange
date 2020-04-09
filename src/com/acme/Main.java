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

        // Obtain some fiat currencies and probabilities
        FiatCurrency RON = (FiatCurrency) currencyRepository.findCurrencyByCode("RON").get();
        FiatCurrency USD = (FiatCurrency) currencyRepository.findCurrencyByCode("USD").get();
        FiatCurrency EUR = (FiatCurrency) currencyRepository.findCurrencyByCode("EUR").get();
        FiatCurrency GBP = (FiatCurrency) currencyRepository.findCurrencyByCode("GBP").get();
        Commodity Gold = (Commodity) currencyRepository.findCurrencyByCode("XAU").get();

        // Make an initial deposit into our inventory
        inventoryService.depositIntoInventory(new Figure(RON, 50000));
        inventoryService.depositIntoInventory(new Figure(USD, 1000));
        inventoryService.depositIntoInventory(new Figure(EUR, 310.75));

        // Print out the current inventory, as well as RON funds
        System.out.println(inventoryService.getInventoryFigures());
        System.out.println(inventoryService.getInventoryFigure(RON));

        // Withdraw 50 RON from the inventory
        inventoryService.withdrawFromInventory(new Figure(RON, 50));
        System.out.println(inventoryService.getInventoryFigure(RON));

        // Check the total value of the inventory in the base currency
        Figure initialFigure = inventoryService.reportTotalInventoryValue();
        System.out.println(initialFigure);

        // Check the necessary funds for buying, selling and exchanging
        ExchangeService exchangeService = ExchangeService.getInstance();
        System.out.println(exchangeService.viewBuy(new Figure(EUR, 100)));
        System.out.println(exchangeService.viewSell(new Figure(EUR, 100)));
        System.out.println(exchangeService.viewExchange(new Figure(RON, 100), EUR));

        // Buy 100 EUR
        exchangeService.buy(new Figure(EUR, 100));
        System.out.println(inventoryService.getInventoryFigures());

        // Buy 100g of Gold
        exchangeService.buy(new Figure(Gold, 100));
        System.out.println(inventoryService.getInventoryFigures());

        // Buy 20 GBP
        exchangeService.buy(new Figure(GBP, 100));
        System.out.println(inventoryService.getInventoryFigures());

        // Sell 100 EUR
        exchangeService.sell(new Figure(EUR, 100));
        System.out.println(inventoryService.getInventoryFigures());

        // Exchange 100 RON into EUR
        exchangeService.exchange(new Figure(RON, 100), EUR);
        System.out.println(inventoryService.getInventoryFigures());

        // View the set of all currencies
        System.out.println(currencyRepository.getCurrencies());

        // Check the final total value of the inventory and compute the profit
        Figure finalFigure = inventoryService.reportTotalInventoryValue();
        System.out.println(finalFigure);

        System.out.print("Profit: ");
        System.out.println(finalFigure.subtract(initialFigure));

        // Try to withdraw 1000000 RON
        System.out.println(inventoryService.getInventoryFigures());
        inventoryService.withdrawFromInventory(new Figure(RON, 1000000));
        System.out.println(inventoryService.getInventoryFigures());
    }
}
