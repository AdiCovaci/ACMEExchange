package model;

public class Figure {
    private Currency currency;
    private double amount;

    public Figure(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Figure add(Figure figure) {
        if (currency.equals(figure.currency))
            amount += figure.amount;

        return this;
    }

    public Figure subtract(Figure figure) {
        if (currency.equals(figure.currency))
            amount -= figure.amount;

        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%.2f ", amount) + currency.toString();
    }
}
