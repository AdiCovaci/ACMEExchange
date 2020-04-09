package model;

import java.util.Comparator;
import java.util.Objects;

public class Currency {
    private String name;
    private String code;

    public static Comparator<Currency> currencyComparator = Comparator.comparing(Currency::getCode);

    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return code.equals(currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return code;
    }
}
