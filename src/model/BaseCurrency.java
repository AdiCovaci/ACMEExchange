package model;

public class BaseCurrency extends FiatCurrency {
    private BaseCurrency() {
        super("Romanian New Leu", "RON", "", 0.01);
    }

    public static BaseCurrency getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static BaseCurrency INSTANCE = new BaseCurrency();
    }
}
