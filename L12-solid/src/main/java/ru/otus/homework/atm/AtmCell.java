package ru.otus.homework.atm;

public interface AtmCell {
    int getBanknotesCount();

    double getBanknotesValueSum();

    double giveBanknotes(int count);

    void putBanknote();

    void putBanknotes(int count);

    Banknote getBanknote();
}
