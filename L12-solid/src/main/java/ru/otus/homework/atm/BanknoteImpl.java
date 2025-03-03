package ru.otus.homework.atm;

import java.util.Objects;

public class BanknoteImpl implements Banknote {

    private final Nominal nominal;

    public BanknoteImpl(Nominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public int getNominal() {
        return nominal.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BanknoteImpl banknote)) return false;
        return getNominal() == banknote.getNominal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNominal());
    }
}
