package ru.otus;

public class ProxyDemo {

    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestClass();
        testLogging.calculation(3);
        testLogging.calculation(2, 3);
        testLogging.calculation(2, 3, "Hello");
    }
}
