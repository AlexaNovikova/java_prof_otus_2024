package ru.otus.homework.atm;

import java.util.Map;
import ru.otus.homework.exceptions.AtmIssueBanknoteException;

public interface AtmMachine {

    Map<Banknote, Integer> giveBanknotes(double sum) throws AtmIssueBanknoteException;

    void putBanknote(Banknote banknote);

    void putBanknotes(Banknote banknote, int count);

    double showBalance();

    Map<Banknote, Integer> getAllBanknotesWithCount();
}
