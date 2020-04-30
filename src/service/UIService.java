package service;

import exception.InsufficientFundsException;
import model.*;
import repositories.CurrencyRepository;
import repositories.ExchangeRateRepository;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Arrays;

public class UIService {
    private InventoryService inventoryService = InventoryService.getInstance();
    private ExchangeService exchangeService = ExchangeService.getInstance();
    private AuditService auditService = AuditService.getInstance();
    private CurrencyRepository currencyRepository = CurrencyRepository.getInstance();
    private ExchangeRateRepository exchangeRateRepository = ExchangeRateRepository.getInstance();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void run() throws IOException {
        System.out.println("=== WELCOME to ACME Exchange ===");
        System.out.println("What menu do you want to open?");
        System.out.println("\t1. Open the Client Menu");
        System.out.println("\t2. Open the Admin Menu");
        System.out.println("\t9. Exit");
        String resp = reader.readLine();
        switch (Integer.parseInt(resp)) {
            case 1:
                openClientMenu();
                break;
            case 2:
                System.out.println("Type the password");
                char[] pass = reader.readLine().toCharArray();
                if (Arrays.equals(pass, "pswd".toCharArray())) {
                    openAdminMenu();
                } else {
                    inventoryService.saveInventory();
                }
                break;
            case 9:
                inventoryService.saveInventory();
                break;
            case 0:
                runDemo();
                break;
        }
    }

    private void openClientMenu() throws IOException {
        String resp = "0";
        while(!resp.equals("9")) {
            System.out.println("What do you want to do?");
            System.out.println("\t1. SELL");
            System.out.println("\t2. BUY");
            System.out.println("\t3. EXCHANGE");
            System.out.println("\t9. Exit");

            resp = reader.readLine();

            Currency currency;
            Figure figure;

            switch (Integer.parseInt(resp)) {
                case 1:
                    System.out.println("Check how much RON you would get for {Currency} {Amount}");
                    System.out.println("Currency=");
                    currency = currencyRepository.findCurrencyByCode(reader.readLine()).get();
                    System.out.println("Amount=");
                    figure = new Figure(currency, Integer.parseInt(reader.readLine()));

                    System.out.println("You would recieve: " + exchangeService.viewBuy(figure));

                    System.out.println("Continue? [Y/N]");
                    if (reader.readLine().equals("Y")) {
                        exchangeService.buy(figure);
                    }
                    break;
                case 2:
                    System.out.println("Check how much RON you would need to pay for {Currency} {Amount}");
                    System.out.println("Currency=");
                    currency = currencyRepository.findCurrencyByCode(reader.readLine()).get();
                    System.out.println("Amount=");
                    figure = new Figure(BaseCurrency.getInstance(), Integer.parseInt(reader.readLine()));

                    System.out.println("You would need to pay: " + exchangeService.viewSell(figure));

                    System.out.println("Continue? [Y/N]");
                    if (reader.readLine().equals("Y")) {
                        exchangeService.sell(figure);
                    }
                    break;
                case 3:
                    System.out.println("Check how much {Currency} you would get for RON {Amount}");
                    System.out.println("Currency=");
                    currency = currencyRepository.findCurrencyByCode(reader.readLine()).get();
                    System.out.println("Amount=");
                    figure = new Figure(BaseCurrency.getInstance(), Integer.parseInt(reader.readLine()));

                    System.out.println("You would recieve: " + exchangeService.viewExchange(figure, currency));

                    System.out.println("Continue? [Y/N]");
                    if (reader.readLine().equals("Y")) {
                        exchangeService.exchange(figure, currency);
                    }
                    break;
            }
        }

        inventoryService.saveInventory();
    }

    private void openAdminMenu() throws IOException {
        String resp = "0";
        while(!resp.equals("9")) {
            System.out.println("What do you want to do?");
            System.out.println("\t1. REPORT TOTAL inventory value");
            System.out.println("\t2. REPORT inventory figures");
            System.out.println("\t3. DEPOSIT");
            System.out.println("\t4. WITHDRAW");
            System.out.println("\t5. SAVE");
            System.out.println("\t9. Exit");

            resp = reader.readLine();

            Currency currency;
            Figure figure;

            switch (Integer.parseInt(resp)) {
                case 1:
                    System.out.println("Total inventory value: " + inventoryService.reportTotalInventoryValue());
                    break;
                case 2:
                    System.out.println("Report inventory figure for {Currency} [Leave Currency blank to see all]");
                    System.out.println("Currency=");
                    String c = reader.readLine();
                    if (c.equals("")) {
                        System.out.println(inventoryService.getInventoryFigures());
                    } else {
                        currency = currencyRepository.findCurrencyByCode(c).get();
                        System.out.println(inventoryService.getInventoryFigure(currency));
                    }
                    break;
                case 3:
                    System.out.println("Deposit {Currency} {Amount} into inventory");
                    System.out.println("Currency=");
                    currency = currencyRepository.findCurrencyByCode(reader.readLine()).get();
                    System.out.println("Amount=");
                    figure = new Figure(BaseCurrency.getInstance(), Integer.parseInt(reader.readLine()));

                    inventoryService.depositIntoInventory(figure);
                    break;
                case 4:
                    System.out.println("Withdraw {Currency} {Amount} from inventory");
                    System.out.println("Currency=");
                    currency = currencyRepository.findCurrencyByCode(reader.readLine()).get();
                    System.out.println("Amount=");
                    figure = new Figure(BaseCurrency.getInstance(), Integer.parseInt(reader.readLine()));

                    inventoryService.withdrawFromInventory(figure);
                    break;
                case 5:
                    inventoryService.saveInventory();
                    break;
            }
        }

        inventoryService.saveInventory();
    }

    private void runDemo() {
        System.out.println(inventoryService.getInventoryFigures());

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

        // Try to exchange 100 EUR into USD
//        exchangeService.exchange(new Figure(EUR, 100), USD);
//        System.out.println(inventoryService.getInventoryFigures());

        inventoryService.saveInventory();
    }

    public static UIService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static UIService INSTANCE = new UIService();
    }
}