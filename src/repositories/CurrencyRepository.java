package repositories;

import model.BaseCurrency;
import model.Commodity;
import model.Currency;
import model.FiatCurrency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class CurrencyRepository {
    private Set<Currency> currencies = new TreeSet<>(Currency.currencyComparator);
    private final String fiat_file = "data/fiat_currencies.csv";
    private final String comm_file = "data/commodities.csv";

    private CurrencyRepository() {
        currencies.add(BaseCurrency.getInstance());

        Path fiat_path = Paths.get(fiat_file);

        try {
            if (!Files.exists(fiat_path)) {
                throw new FileNotFoundException();
            }

            var cList = Files.readAllLines(fiat_path);

            for (String c : cList) {
                String[] cProps = c.split(",");

                FiatCurrency currency = new FiatCurrency(
                        cProps[1],
                        cProps[0],
                        cProps[2],
                        Double.parseDouble(cProps[3])
                );

                currencies.add(currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path comm_path = Paths.get(comm_file);

        try {
            if (!Files.exists(comm_path)) {
                throw new FileNotFoundException();
            }

            var cList = Files.readAllLines(comm_path);

            for (String c : cList) {
                String[] cProps = c.split(",");

                Commodity currency = new Commodity(
                        cProps[1],
                        cProps[0],
                        cProps[2]
                );

                currencies.add(currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(currencies);
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public Optional<Currency> findCurrencyByCode(String code) {
        List<Currency> result = currencies.stream()
                .filter(c -> c.getCode().equals(code))
                .collect(Collectors.toList());

        if (result.size() == 0)
            return Optional.empty();

        return Optional.of(result.get(0));

    }

    public static CurrencyRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static CurrencyRepository INSTANCE = new CurrencyRepository();
    }
}
