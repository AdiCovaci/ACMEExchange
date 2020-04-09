package model;

import exception.DifferentCurrencyException;

public class Figure {
    private Currency currency;
    private double amount;

    public Figure(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Figure add(Figure figure) {
        if (currency.equals(figure.currency))
            return new Figure(currency, amount + figure.amount);

        throw new DifferentCurrencyException();
    }

    public Figure subtract(Figure figure) {
        if (currency.equals(figure.currency))
            return new Figure(currency, amount - figure.amount);

        throw new DifferentCurrencyException();
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isNegative() {
        return amount < 0;
    }

    @Override
    public String toString() {
        return currency.formatFigure(amount);
    }
}
