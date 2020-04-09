package exception;

public class ExchangeBetweenNonBaseCurrenciesException extends RuntimeException {
    public ExchangeBetweenNonBaseCurrenciesException() {
        super("Cannot exchange between two non-base currencies");
    }
}