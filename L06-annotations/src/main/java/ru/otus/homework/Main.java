package ru.otus.homework;

import ru.otus.homework.test.MyIntCalculatorTests;
import ru.otus.homework.test.SimpleTests;
import ru.otus.homework.test.TestRunner;

public class Main {
    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        testRunner.runTests(MyIntCalculatorTests.class);
        testRunner.runTests(SimpleTests.class);
    }
}
