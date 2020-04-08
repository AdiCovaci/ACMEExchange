package service;

import model.BaseCurrency;
import model.Currency;
import model.ExchangeRate;
import model.Figure;
import repositories.ExchangeRateRepository;

import java.util.Optional;

public class ExchangeService {

    private ExchangeService() {

    }

    public Figure viewBuy(Figure clientFigure) {
        ExchangeRateRepository exchangeRateRepository = ExchangeRateRepository.getInstance();
        Currency clientCurrency = clientFigure.getCurrency();
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findExchangeRateByCurrency(clientCurrency);
        ExchangeRate exchangeRate;

        if (optionalExchangeRate.isPresent())
            exchangeRate = optionalExchangeRate.get();
        else
            throw new Error();

        return exchangeRate.getBuyFigure(clientFigure);
    }

    public Figure viewSell(Figure targetFigure) {
        ExchangeRateRepository exchangeRateRepository = ExchangeRateRepository.getInstance();
        Currency targetCurrency = targetFigure.getCurrency();
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findExchangeRateByCurrency(targetCurrency);
        ExchangeRate exchangeRate;

        if (optionalExchangeRate.isPresent())
            exchangeRate = optionalExchangeRate.get();
        else
            throw new Error();

        return exchangeRate.getSellFigure(targetFigure);
    }

    public Figure viewExchange(Figure clientFigure, Currency targetCurrency) {
        if (!clientFigure.getCurrency().equals(BaseCurrency.getInstance()))
            throw new Error();

        ExchangeRateRepository exchangeRateRepository = ExchangeRateRepository.getInstance();
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findExchangeRateByCurrency(targetCurrency);
        ExchangeRate exchangeRate;

        if (optionalExchangeRate.isPresent())
            exchangeRate = optionalExchangeRate.get();
        else
            throw new Error();

        return exchangeRate.getExchangeFigure(clientFigure, targetCurrency);
    }

    public static ExchangeService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static ExchangeService INSTANCE = new ExchangeService();
    }
}
