package ru.otus.homework.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

public class TestRunner {
    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    public static void runTests(Class<? extends TestClass> testClass) {
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        createMethods(testClass, beforeMethods, tests, afterMethods);
        int successTests = executeTests(testClass, beforeMethods, tests, afterMethods);
        printResult(successTests, tests);
    }

    private static void printResult(int successTests, List<Method> allTests) {
        logger.info(
                "Вызвано тестов - {}, из них успешно - {}, с ошибкой - {}",
                allTests.size(),
                successTests,
                allTests.size() - successTests);
    }

    private static void createMethods(
            Class<? extends TestClass> testClass,
            List<Method> beforeMethods,
            List<Method> tests,
            List<Method> afterMethods) {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Before.class)) {
                    beforeMethods.add(method);
                } else if (annotation.annotationType().equals(After.class)) {
                    afterMethods.add(method);
                } else if (annotation.annotationType().equals(Test.class)) {
                    tests.add(method);
                }
            }
        }
    }

    private static int executeTests(
            Class<? extends TestClass> testClass,
            List<Method> beforeMethods,
            List<Method> tests,
            List<Method> afterMethods) {
        int failed = 0;
        for (Method method : tests) {
            try {
                Constructor<? extends TestClass> constructor = testClass.getConstructor();
                TestClass test = constructor.newInstance();
                for (Method method1 : beforeMethods) {
                    method1.invoke(test);
                }
                try {
                    method.invoke(test);
                } catch (Exception e) {
                    failed++;
                }
                for (Method method1 : afterMethods) {
                    method1.invoke(test);
                }
            } catch (NoSuchMethodException
                    | InvocationTargetException
                    | InstantiationException
                    | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return tests.size() - failed;
    }
}
