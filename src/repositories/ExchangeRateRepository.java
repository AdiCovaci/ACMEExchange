package repositories;

import model.Currency;
import model.ExchangeRate;
import model.FiatCurrency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepository {
    private List<ExchangeRate> exchangeRates = new ArrayList<>();
    private final String file = "data/exchange_rates.csv";

    private ExchangeRateRepository() {
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();

        Path path = Paths.get(file);

        try {
            if (!Files.exists(path)) {
                throw new FileNotFoundException();
            }

            var xList = Files.readAllLines(path);

            for (String x : xList) {
                String[] xProps = x.split(",");

                ExchangeRate exchangeRate = new ExchangeRate(
                        currencyRepository.findCurrencyByCode(xProps[0]).get(),
                        Double.parseDouble(xProps[1]),
                        Double.parseDouble(xProps[2]),
                        Double.parseDouble(xProps[3])
                );

                exchangeRates.add(exchangeRate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<ExchangeRate> findExchangeRateByCurrency(Currency currency) {
        for (ExchangeRate e : exchangeRates)
            if (e.getCurrency().equals(currency))
                return Optional.of(e);

        return Optional.empty();
    }

    public Optional<ExchangeRate> findExchangeRateByCode(String code) {
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();
        Optional<Currency> optionalCurrency = currencyRepository.findCurrencyByCode(code);
        Currency currency;

        if (optionalCurrency.isPresent())
            currency = optionalCurrency.get();
        else
            return Optional.empty();

        for (ExchangeRate e : exchangeRates)
            if (e.getCurrency().equals(currency))
                return Optional.of(e);

        return Optional.empty();
    }

    public static ExchangeRateRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static ExchangeRateRepository INSTANCE = new ExchangeRateRepository();
    }
}
