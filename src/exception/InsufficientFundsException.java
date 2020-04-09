package exception;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Not enough funds in inventory for the current operation");
    }
}