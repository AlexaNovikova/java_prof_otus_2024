package ru.otus.homework.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResult {

    private final HashMap<Method, Boolean> methodsResult;
    private static final Logger logger = LoggerFactory.getLogger(TestResult.class);

    public TestResult() {
        this.methodsResult = new HashMap<>();
    }

    public TestResult(HashMap<Method, Boolean> methodsResult) {
        this.methodsResult = methodsResult;
    }

    public void addMethodResult(Method method, Boolean result) {
        methodsResult.put(method, result);
    }

    public void printResult() {
        logger.info(
                "Вызвано тестов - {}, из них успешно - {}, с ошибкой - {}",
                methodsResult.size(),
                methodsResult.entrySet().stream()
                        .filter(e -> e.getValue().equals(true))
                        .count(),
                methodsResult.entrySet().stream()
                        .filter(e -> e.getValue().equals(false))
                        .count());
    }
}
