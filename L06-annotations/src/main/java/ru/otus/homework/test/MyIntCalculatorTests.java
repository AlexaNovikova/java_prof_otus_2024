package ru.otus.homework.test;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;
import ru.otus.homework.calculator.MyIntCalculator;

public class MyIntCalculatorTests implements TestClass {

    private static final Logger logger = LoggerFactory.getLogger(MyIntCalculatorTests.class);

    @Before
    public void init() {
        System.out.println("@BeforeEach 1");
    }

    @Before
    public void init2() {
        System.out.println("@BeforeEach 2");
    }

    @Test
    public void testAdd() {
        System.out.println("Run test");
        Assertions.assertEquals(5, MyIntCalculator.add(2, 3));
    }

    @Test
    public void testSub() {
        System.out.println("Run test");
        Assertions.assertEquals(5, MyIntCalculator.sub(8, 3));
    }

    @Test
    public void testException() {
        System.out.println("Run test");
        throw new ArithmeticException("Ошибка");
    }

    @Test
    public void testDiv() {
        System.out.println("Run test");
        Assertions.assertEquals(5, MyIntCalculator.div(8, 2));
    }

    @After
    public void after1() {
        System.out.println("@AfterEach 1");
    }

    @After
    public void after2() {
        System.out.println("@AfterEach 2");
    }
}
