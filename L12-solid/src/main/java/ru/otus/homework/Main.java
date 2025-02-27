package ru.otus.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ru.otus.homework.atm.*;

public class Main {

    public static void main(String[] args) {

        List<Banknote> banknotes = createList();
        AtmCellsCreator atmCellsCreator = new AtmCellsCreatorImpl();
        BanknotesCalculator banknotesCalculator = new BanknotesCalculatorImpl();
        AtmMachine atmMachine = new AtmMachineImpl(banknotes, atmCellsCreator, banknotesCalculator);

        System.out.println("Put 10 banknotes with nominal 10 in ATM machine.");
        for (int i = 0; i < 10; i++) {
            atmMachine.putBanknote(new BanknoteImpl(Nominal.TEN));
        }
        showBalance(atmMachine);

        System.out.println("Put 5 banknotes with nominal 200 in ATM machine.");
        atmMachine.putBanknotes(new BanknoteImpl(Nominal.TWO_HUNDREDS), 5);
        showBalance(atmMachine);

        System.out.println("Request sum of 210.00 from ATM machine.");
        atmMachine.giveBanknotes(210);
        showBalance(atmMachine);

        System.out.println("Request sum of 230.00 from ATM machine.");
        atmMachine.giveBanknotes(230);
        showBalance(atmMachine);
        showBanknotes(atmMachine);
    }

    private static List<Banknote> createList() {
        return new ArrayList<>(
                Arrays.stream(Nominal.values()).map(BanknoteImpl::new).toList());
    }

    private static void showBalance(AtmMachine atmMachine) {
        System.out.println("Balance: " + atmMachine.showBalance());
    }

    private static void showBanknotes(AtmMachine atmMachine) {
        System.out.println("AtmMachine banknotes:");
        Map<Banknote, Integer> banknotesMap = atmMachine.getAllBanknotesWithCount();
        for (Map.Entry<Banknote, Integer> entry : banknotesMap.entrySet()) {
            System.out.println(
                    "Banknote with nominal: " + entry.getKey().getNominal() + ", count: " + entry.getValue());
        }
    }
}
