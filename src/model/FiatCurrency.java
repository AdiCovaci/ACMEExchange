package model;

public class FiatCurrency extends Currency {
    private String sign;
    private double subunit;

    public FiatCurrency(String name, String code, String sign, double subunit) {
        super(name, code);
        this.sign = sign;
        this.subunit = subunit;
    }

    public String formatFigure(double amount) {
        if (sign.equals(""))
            return String.format("%.2f ", amount) + code;

        return sign + String.format("%.2f", amount);
    }
}
