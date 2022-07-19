package ru.otus.exception;

public class NotEnoughBanknotesException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Not enough banknotes to issue the requested amount";
    }
}
