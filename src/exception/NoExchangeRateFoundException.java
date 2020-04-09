package exception;

public class NoExchangeRateFoundException extends RuntimeException {
    public NoExchangeRateFoundException() {
        super("No exchange rate found for this currency");
    }
}