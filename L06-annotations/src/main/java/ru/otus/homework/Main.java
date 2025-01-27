package ru.otus.homework;

import ru.otus.homework.test.MyIntCalculatorTests;
import ru.otus.homework.test.SimpleTests;
import ru.otus.homework.test.TestRunner;

public class Main {
    public static void main(String[] args) {
        TestRunner.runTests(MyIntCalculatorTests.class);
        TestRunner.runTests(SimpleTests.class);
    }
}
