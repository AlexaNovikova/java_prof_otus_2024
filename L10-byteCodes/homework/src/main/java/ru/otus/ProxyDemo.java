package ru.otus;

public class ProxyDemo {

    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createMyClass();
        testLogging.calculation(3);
        testLogging.calculation(2, 3);
        testLogging.calculation(2, 3, "sdfsadf");
    }

    public static class Demo {
        public void action() {
            new TestLogging().calculation(2);
        }
    }
}
