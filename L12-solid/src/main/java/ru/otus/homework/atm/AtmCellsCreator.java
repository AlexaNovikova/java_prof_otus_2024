package ru.otus.homework.atm;

import java.util.List;
import java.util.Map;

public interface AtmCellsCreator {
    Map<Integer, AtmCell> createMap(List<Banknote> banknotes);
}
