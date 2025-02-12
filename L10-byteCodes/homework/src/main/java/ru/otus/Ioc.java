package ru.otus;

import java.lang.reflect.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    static TestLoggingInterface createTestClass() {
        InvocationHandler handler = new TestInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class TestInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        private final Set<String> methodsWithLogAnnotationSet = new HashSet<>();

        TestInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            Method[] methods = testLogging.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (Arrays.stream(method.getAnnotations())
                        .anyMatch(a -> a.annotationType().equals(Log.class))) {
                    methodsWithLogAnnotationSet.add(method.getName() + Arrays.toString(method.getParameters()));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodsWithLogAnnotationSet.contains(method.getName() + Arrays.toString(method.getParameters()))) {
                StringBuilder sb = new StringBuilder();
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    sb.append("param")
                            .append(i + 1)
                            .append(": ")
                            .append(args[i])
                            .append(", ");
                }
                logger.info("executed method: {}, {}", method.getName(), sb.substring(0, sb.length() - 2));
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "TestInvocationHandler{" + "class=" + testLogging + '}';
        }
    }
}
