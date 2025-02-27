package ru.otus.homework.atm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.homework.exceptions.AtmIssueBanknoteException;

public class AtmMachineImpl implements AtmMachine {

    private final Map<Integer, AtmCell> atmCellMap;
    private final BanknotesCalculator banknotesCalculator;

    public AtmMachineImpl(
            List<Banknote> banknotes, AtmCellsCreator atmCellsCreator, BanknotesCalculator banknotesCalculator) {
        atmCellMap = atmCellsCreator.createMap(banknotes);
        this.banknotesCalculator = banknotesCalculator;
    }

    @Override
    public Map<Banknote, Integer> giveBanknotes(double sum) throws AtmIssueBanknoteException {
        return banknotesCalculator.giveBanknotesFromAtmCellsMap(atmCellMap, sum);
    }

    @Override
    public void putBanknote(Banknote banknote) {
        AtmCell atmCell = atmCellMap.get(banknote.getNominal());
        atmCell.putBanknote();
    }

    @Override
    public void putBanknotes(Banknote banknote, int count) {
        AtmCell atmCell = atmCellMap.get(banknote.getNominal());
        atmCell.putBanknotes(count);
    }

    @Override
    public double showBalance() {
        return banknotesCalculator.calculateFullBalance(atmCellMap);
    }

    @Override
    public Map<Banknote, Integer> getAllBanknotesWithCount() {
        Map<Banknote, Integer> banknotesMap = new HashMap<>();
        for (Map.Entry<Integer, AtmCell> atmCellEntry : atmCellMap.entrySet()) {
            banknotesMap.put(
                    atmCellEntry.getValue().getBanknote(),
                    atmCellEntry.getValue().getBanknotesCount());
        }
        return banknotesMap;
    }
}
