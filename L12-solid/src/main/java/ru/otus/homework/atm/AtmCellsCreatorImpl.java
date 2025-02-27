package ru.otus.homework.atm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmCellsCreatorImpl implements AtmCellsCreator {

    public Map<Integer, AtmCell> createMap(List<Banknote> banknotes) {
        Map<Integer, AtmCell> atmCelMap = new HashMap<>();
        for (Banknote banknote : banknotes) {
            atmCelMap.put(banknote.getNominal(), createCell(banknote));
        }
        return atmCelMap;
    }

    private AtmCell createCell(Banknote banknote) {
        return new AtmCellImpl(banknote);
    }
}
