package ru.otus;

import java.lang.reflect.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    static TestLoggingInterface createMyClass() {
        boolean needToCreateProxy = Arrays.stream(TestLogging.class.getDeclaredMethods())
                .anyMatch(m -> Arrays.stream(m.getAnnotations())
                        .anyMatch(a -> a.annotationType().equals(Log.class)));
        if (needToCreateProxy) {
            InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
            return (TestLoggingInterface) Proxy.newProxyInstance(
                    Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
        }
        return new TestLogging();
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        Set<String> methodsSet = new HashSet<>();

        DemoInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            Method[] methods = testLogging.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (Arrays.stream(method.getAnnotations())
                        .anyMatch(a -> a.annotationType().equals(Log.class))) {
                    methodsSet.add(method.getName() + Arrays.toString(method.getParameters()));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodsSet.contains(method.getName() + Arrays.toString(method.getParameters()))) {
                StringBuilder sb = new StringBuilder("");
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    sb.append("param").append(i + 1).append(": ").append(args[i]);
                }
                logger.info("executed method: {}, {}", method.getName(), sb);
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + testLogging + '}';
        }
    }
}
