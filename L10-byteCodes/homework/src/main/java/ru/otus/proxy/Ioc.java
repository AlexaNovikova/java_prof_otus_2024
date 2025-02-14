package ru.otus.proxy;

import java.lang.reflect.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;
import ru.otus.utils.ReflectionHelper;

public class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    @SuppressWarnings("unchecked")
    public static <T> T createClass(Class<? extends T> clazz) {
        var classInstance = ReflectionHelper.newInstance(clazz);
        var handler = new TestInvocationHandler<>(classInstance);
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), clazz.getInterfaces(), handler);
    }

    static class TestInvocationHandler<T> implements InvocationHandler {
        private final T testClass;
        private final Set<String> methodsWithLogAnnotationSet = new HashSet<>();

        TestInvocationHandler(T instance) {
            this.testClass = instance;
            fillMethodsSet();
        }

        private void fillMethodsSet() {
            Method[] methods = testClass.getClass().getDeclaredMethods();
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
            return method.invoke(testClass, args);
        }

        @Override
        public String toString() {
            return "TestInvocationHandler{" + "class=" + testClass + '}';
        }
    }
}
