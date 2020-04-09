package model;

public class ExchangeRate {
    private Currency currency;
    private double buyRate;
    private double sellRate;
    private double trueRate;

    public ExchangeRate(Currency currency, double buyRate, double sellRate, double trueRate) {
        this.currency = currency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.trueRate = trueRate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Figure getBuyFigure(Figure clientFigure) {
        return new Figure(BaseCurrency.getInstance(), clientFigure.getAmount() * buyRate);
    }

    public Figure getSellFigure(Figure targetFigure) {
        return new Figure(BaseCurrency.getInstance(), targetFigure.getAmount() * sellRate);
    }

    public Figure getExchangeFigure(Figure clientFigure, Currency targetCurrency) {
        return new Figure(targetCurrency, clientFigure.getAmount() / sellRate);
    }

    public Figure getRealFigure(Figure exchangedFigure) {
        return new Figure(BaseCurrency.getInstance(), exchangedFigure.getAmount() * trueRate);
    }
}
