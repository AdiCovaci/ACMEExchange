package model;

public class Commodity extends Currency {
    private String unit;
    public Commodity(String name, String code, String unit) {
        super(name, code);
        this.unit = unit;
    }

    public String formatFigure(double amount) {
        return String.format("%.2f", amount) + unit + " " + name;
    }
}