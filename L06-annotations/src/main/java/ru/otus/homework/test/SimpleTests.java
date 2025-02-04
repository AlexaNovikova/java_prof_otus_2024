package ru.otus.homework.test;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

public class SimpleTests implements TestClass {
    @Before
    public void init() {
        System.out.println("@BeforeEach 1");
    }

    @Before
    public void init2() {
        System.out.println("@BeforeEach 2");
    }

    @Test
    public void test1() {
        System.out.println("Run test");
    }

    @Test
    public void test2() {
        System.out.println("Run test");
    }

    @Test
    public void test3() {
        System.out.println("Run test");
        throw new ArrayIndexOutOfBoundsException("Ошибка");
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
