package ru.otus.homework.atm;

import ru.otus.homework.exceptions.AtmCellIssueBanknoteException;

public class AtmCellImpl implements AtmCell {

    private int count;
    private final Banknote banknote;

    public AtmCellImpl(Banknote banknote) {
        this.banknote = banknote;
        this.count = 0;
    }

    @Override
    public int getBanknotesCount() {
        return count;
    }

    @Override
    public double getBanknotesValueSum() {
        return count * banknote.getNominal();
    }

    @Override
    public double giveBanknotes(int count) {
        if (getBanknotesCount() < count) {
            throw new AtmCellIssueBanknoteException("It is impossible to give such count");
        }
        this.count -= count;
        return count * banknote.getNominal();
    }

    @Override
    public void putBanknote() {
        count++;
    }

    @Override
    public void putBanknotes(int count) {
        this.count += count;
    }

    @Override
    public Banknote getBanknote() {
        return banknote;
    }
}
