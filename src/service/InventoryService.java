package service;

import model.*;
import repositories.CurrencyRepository;
import repositories.ExchangeRateRepository;

import java.util.Map;

public class InventoryService {
    private Inventory inventory;

    public InventoryService() {
        inventory = Inventory.getInstance();
    }

    public void depositIntoInventory(Figure figure) {
        inventory.deposit(figure);
    }

    public void withdrawFromInventory(Figure figure) {
        try {
            inventory.withdraw(figure);
        } catch (Inventory.InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Currency, Figure> getInventoryFigures() {
        return inventory.getFigures();
    }

    public Figure getInventoryFigure(Currency currency) {
        return inventory.getFigure(currency);
    }

    public Figure reportTotalInventoryValue() {
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();
        ExchangeRateRepository exchangeRateRepository = ExchangeRateRepository.getInstance();

        Figure value = new Figure(BaseCurrency.getInstance(), 0);

        for (Currency currency : currencyRepository.getCurrencies()) {
            if (currency.equals(BaseCurrency.getInstance())) {
                value = value.add(inventory.getFigure(currency));
            } else {
                ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCurrency(currency).get();
                value = value.add(exchangeRate.getRealFigure(inventory.getFigure(currency)));
            }
        }

        return value;
    }

    public static InventoryService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static InventoryService INSTANCE = new InventoryService();
    }
}
