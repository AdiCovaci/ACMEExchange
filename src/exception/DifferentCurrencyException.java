package exception;

public class DifferentCurrencyException extends RuntimeException {
    public DifferentCurrencyException() {
        super("Cannot apply operations on figures having different currencies");
    }
}
