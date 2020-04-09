package repositories;

import model.BaseCurrency;
import model.Currency;

import java.util.*;

public class CurrencyRepository {
    private Set<Currency> currencies = new TreeSet<>(Currency.currencyComparator);

    private CurrencyRepository() {
        currencies.add(BaseCurrency.getInstance());
        currencies.add(new Currency("Romanian New Leu", "RON"));
        currencies.add(new Currency("United States Dollar", "USD"));
        currencies.add(new Currency("British Pound", "GBP"));
        currencies.add(new Currency("Euro", "EUR"));
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public Optional<Currency> findCurrencyByCode(String code) {
        for (Currency c : currencies)
            if (c.getCode().equals(code))
                return Optional.of(c);

        return Optional.empty();
    }

    public static CurrencyRepository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static CurrencyRepository INSTANCE = new CurrencyRepository();
    }
}
