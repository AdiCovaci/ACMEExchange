package repositories;

import model.Currency;
import model.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepository {
    private List<ExchangeRate> exchangeRates = new ArrayList<>();

    private ExchangeRateRepository() {
        CurrencyRepository currencyRepository = CurrencyRepository.getInstance();

        exchangeRates.add(
            new ExchangeRate(
                    currencyRepository.findCurrencyByCode("EUR").get(),
                    4.75,
                    4.85,
                    4.8
            )
        );

        exchangeRates.add(
            new ExchangeRate(
                    currencyRepository.findCurrencyByCode("USD").get(),
                    3.5,
                    3.6,
                    3.7
            )
        );

        exchangeRates.add(
            new ExchangeRate(
                    currencyRepository.findCurrencyByCode("GBP").get(),
                    5.75,
                    5.82,
                    5.9
            )
        );
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
