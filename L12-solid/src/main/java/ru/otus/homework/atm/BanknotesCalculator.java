package ru.otus.homework.atm;

import java.util.Map;

public interface BanknotesCalculator {
    Map<Banknote, Integer> giveBanknotesFromAtmCellsMap(Map<Integer, AtmCell> atmCellMap, double sum);

    double calculateFullBalance(Map<Integer, AtmCell> atmCellMap);
}
