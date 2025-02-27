package ru.otus.homework.atm;

public enum Nominal {
    TEN(10),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDREDS(200),
    FIVE_HUNDREDS(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSANDS(5000);

    private final int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
