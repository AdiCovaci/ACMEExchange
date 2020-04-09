package repositories;

import model.BaseCurrency;
import model.Commodity;
import model.Currency;
import model.FiatCurrency;

import java.util.*;

public class CurrencyRepository {
    private Set<Currency> currencies = new TreeSet<>(Currency.currencyComparator);

    private CurrencyRepository() {
        currencies.add(BaseCurrency.getInstance());
        currencies.add(new FiatCurrency("United States Dollar", "USD", "$", 0.01));
        currencies.add(new FiatCurrency("British Pound", "GBP", "£", 0.01));
        currencies.add(new FiatCurrency("Euro", "EUR", "€", 0.01));
        currencies.add(new Commodity("Gold", "XAU"));
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
