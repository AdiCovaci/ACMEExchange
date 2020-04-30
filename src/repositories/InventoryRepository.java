package repositories;

import model.Currency;
import model.ExchangeRate;
import model.Figure;
import model.Inventory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryRepository {
    private Inventory inventory = Inventory.getInstance();
    private final String file = "data/inventory.csv";

    private InventoryRepository() {
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();

        Path path = Paths.get(file);

        try {
            if (!Files.exists(path)) {
                throw new FileNotFoundException();
            }

            var fList = Files.readAllLines(path);

            for (String f : fList) {
                String[] fProps = f.split(",");

                Figure figure = new Figure(
                        currencyRepository.findCurrencyByCode(fProps[0]).get(),
                        Double.parseDouble(fProps[1])
                );

                inventory.deposit(figure);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        File inventory_file = new File(file);

        try {
            if (!inventory_file.exists()) {
                inventory_file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(inventory_file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (var figure : inventory.getFigures().entrySet()) {
                bufferedWriter.write(figure.getKey().getCode() + "," + figure.getValue().getAmount() + "\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InventoryRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static InventoryRepository INSTANCE = new InventoryRepository();
    }
}
