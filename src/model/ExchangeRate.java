package model;

public class ExchangeRate {
    private Currency clientCurrency;
    private Currency tellerCurrency;
    private float buyRate;
    private float sellRate;
    private float trueRate;

    public ExchangeRate(Currency clientCurrency, Currency tellerCurrency, float buyRate, float sellRate, float trueRate) {
        this.clientCurrency = clientCurrency;
        this.tellerCurrency = tellerCurrency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.trueRate = trueRate;
    }
}
