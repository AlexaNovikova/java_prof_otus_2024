package ru.otus.homework.exceptions;

public class AtmCellIssueBanknoteException extends RuntimeException {
    public AtmCellIssueBanknoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public AtmCellIssueBanknoteException(String message) {
        super(message);
    }
}
