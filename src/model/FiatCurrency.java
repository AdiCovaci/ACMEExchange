package model;

public class FiatCurrency extends Currency {
    private String sign;
    private float subunit;

    public FiatCurrency(String name, String code, String sign, float subunit) {
        super(name, code);
        this.sign = sign;
        this.subunit = subunit;
    }
}
