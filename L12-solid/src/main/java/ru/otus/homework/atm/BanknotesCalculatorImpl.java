package ru.otus.homework.atm;

import java.util.*;
import ru.otus.homework.exceptions.AtmIssueBanknoteException;

public class BanknotesCalculatorImpl implements BanknotesCalculator {

    @Override
    public Map<Banknote, Integer> giveBanknotesFromAtmCellsMap(Map<Integer, AtmCell> atmCellMap, double requestedSum) {
        double sum = requestedSum;
        List<AtmCell> atmCellList = atmCellMap.values().stream().toList();
        Map<Banknote, Integer> banknotesMap = new HashMap<>();
        List<AtmCell> notEmptyCells = new ArrayList<>(
                atmCellList.stream().filter(a -> a.getBanknotesCount() > 0).toList());
        notEmptyCells.sort(Comparator.comparingInt(o -> o.getBanknote().getNominal()));
        notEmptyCells = notEmptyCells.reversed();
        for (int i = 0; i < notEmptyCells.size(); i++) {
            AtmCell atmCell = notEmptyCells.get(i);
            if (sum > 0 && sum >= atmCell.getBanknote().getNominal()) {
                int banknotesCount = 0;
                int availableBanknoteCount = atmCell.getBanknotesCount();
                while (sum > 0 && sum >= atmCell.getBanknote().getNominal() && availableBanknoteCount > 0) {
                    banknotesCount++;
                    availableBanknoteCount--;
                    sum -= atmCell.getBanknote().getNominal();
                }
                if (banknotesCount > 0) {
                    banknotesMap.put(atmCell.getBanknote(), banknotesCount);
                }
            }
        }
        if (sum != 0) {
            throw new AtmIssueBanknoteException("It is impossible to give the requested sum: " + requestedSum);
        } else {
            for (Map.Entry<Banknote, Integer> entry : banknotesMap.entrySet()) {
                AtmCell atmCell = atmCellMap.get(entry.getKey().getNominal());
                atmCell.giveBanknotes(entry.getValue());
            }
            return banknotesMap;
        }
    }

    @Override
    public double calculateFullBalance(Map<Integer, AtmCell> atmCellMap) {
        return atmCellMap.values().stream()
                .mapToDouble(AtmCell::getBanknotesValueSum)
                .sum();
    }
}
