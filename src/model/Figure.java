package model;

public class Figure {
    private Currency currency;
    private double amount;

    public Figure(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public void add(Figure figure) {
        if (currency.equals(figure.currency))
            amount += figure.amount;
    }

    public void subtract(Figure figure) {
        if (currency.equals(figure.currency))
            amount -= figure.amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("%.2f ", amount) + currency.toString();
    }
}
