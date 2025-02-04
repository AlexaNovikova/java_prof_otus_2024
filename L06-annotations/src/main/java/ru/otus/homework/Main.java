package ru.otus.homework;

import ru.otus.homework.test.MyIntCalculatorTests;
import ru.otus.homework.test.SimpleTests;
import ru.otus.homework.test.TestResult;
import ru.otus.homework.test.TestRunner;

public class Main {
    public static void main(String[] args) {
        TestResult testResultMyIntCalculatorTest = TestRunner.runTests(MyIntCalculatorTests.class);
        testResultMyIntCalculatorTest.printResult();
        TestResult testResultSimpleTest = TestRunner.runTests(SimpleTests.class);
        testResultSimpleTest.printResult();
    }
}
