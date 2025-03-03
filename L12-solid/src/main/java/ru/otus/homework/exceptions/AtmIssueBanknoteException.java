package ru.otus.homework.exceptions;

public class AtmIssueBanknoteException extends RuntimeException {

    public AtmIssueBanknoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public AtmIssueBanknoteException(String message) {
        super(message);
    }
}
