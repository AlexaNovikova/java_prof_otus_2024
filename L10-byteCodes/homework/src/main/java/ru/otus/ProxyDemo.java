package ru.otus;

public class ProxyDemo {

    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createClass(TestLogging.class);
        TestLoggingInterface anotherClass = Ioc.createClass(AnotherTestClass.class);
        testLogging.calculation(3);
        testLogging.calculation(2, 3);
        testLogging.calculation(2, 3, "Hello");
        anotherClass.calculation(2);
        anotherClass.calculation(1, 2, "asd", 2);
    }
}
