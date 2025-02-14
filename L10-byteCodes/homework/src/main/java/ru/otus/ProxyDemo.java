package ru.otus;

import ru.otus.proxy.Ioc;

public class ProxyDemo {

    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createClass(TestLogging.class);
        AnotherLoggingInterface anotherClass = Ioc.createClass(AnotherTestClass.class);
        testLogging.calculation(3);
        testLogging.calculation(2, 3);
        testLogging.calculation(2, 3, "Hello");
        anotherClass.anotherCalculation(2);
        anotherClass.anotherCalculation(1, 2);
        anotherClass.anotherCalculation(1, 3, "another");
    }
}
