package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumbersPrinter {

    private static final int MAX_VALUE = 10;
    private static final int MIN_VALUE = 1;

    private static final Logger log = LoggerFactory.getLogger(NumbersPrinter.class);
    private int currentThreadNumber = 1;
    private int currentNumber = 1;
    private int decInt = 1;

    private synchronized void action(int threadNumber) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (threadNumber != currentThreadNumber) {
                    this.wait();
                }
                log.info(String.valueOf(currentNumber));

                if (currentThreadNumber == 1) {
                    currentThreadNumber = 2;
                } else {
                    currentThreadNumber = 1;
                    if (currentNumber == MAX_VALUE) {
                        decInt = -1;
                    } else if (currentNumber == MIN_VALUE) {
                        decInt = 1;
                    }
                    currentNumber += decInt;
                }
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        NumbersPrinter numbersPrinter = new NumbersPrinter();
        new Thread(() -> numbersPrinter.action(1)).start();
        new Thread(() -> numbersPrinter.action(2)).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
