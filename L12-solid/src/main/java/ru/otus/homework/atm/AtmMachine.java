package ru.otus.homework.atm;

import java.util.Map;

public interface AtmMachine {

    Map<Banknote, Integer> giveBanknotes(double sum);

    void putBanknote(Banknote banknote);

    void putBanknotes(Banknote banknote, int count);

    double showBalance();

    Map<Banknote, Integer> getAllBanknotesWithCount();
}
