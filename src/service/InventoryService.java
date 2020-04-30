package service;

import exception.InsufficientFundsException;
import model.*;
import repositories.CurrencyRepository;
import repositories.ExchangeRateRepository;
import repositories.InventoryRepository;

import java.util.Map;
import java.util.Optional;

public class InventoryService {
    private Inventory inventory;
    private InventoryRepository inventoryRepository = InventoryRepository.getInstance();
    private AuditService auditService = AuditService.getInstance();

    public InventoryService() {
        inventory = Inventory.getInstance();
    }

    public void depositIntoInventory(Figure figure) {
        auditService.log("deposit into inventory (" + figure + ")");
        inventory.deposit(figure);
    }

    public void withdrawFromInventory(Figure figure) {
        auditService.log("withdraw from inventory (" + figure + ")");
        try {
            inventory.withdraw(figure);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Currency, Figure> getInventoryFigures() {
        auditService.log("view all inventory figures");
        return inventory.getFigures();
    }

    public Figure getInventoryFigure(Currency currency) {
        auditService.log("view inventory for currency: " + currency);
        return inventory.getFigure(currency);
    }

    public Figure reportTotalInventoryValue() {
        auditService.log("report total inventory value");
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();
        ExchangeRateRepository exchangeRateRepository = ExchangeRateRepository.getInstance();

        Figure value = new Figure(BaseCurrency.getInstance(), 0);

        for (Currency currency : currencyRepository.getCurrencies()) {
            if (currency.equals(BaseCurrency.getInstance())) {
                value = value.add(inventory.getFigure(currency));
            } else {
                Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findExchangeRateByCurrency(currency);
                ExchangeRate exchangeRate;

                if (optionalExchangeRate.isPresent()) {
                    exchangeRate = optionalExchangeRate.get();
                } else {
                    System.out.println("No exchange rate for " + currency);
                    continue;
                }

                value = value.add(exchangeRate.getRealFigure(inventory.getFigure(currency)));
            }
        }

        return value;
    }

    public void saveInventory() {
        auditService.log("saved inventory");
        inventoryRepository.save();
    }

    public static InventoryService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static InventoryService INSTANCE = new InventoryService();
    }
}
