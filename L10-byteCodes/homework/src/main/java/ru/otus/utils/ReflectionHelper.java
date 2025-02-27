package ru.otus.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionHelper {

    public static <T> T newInstance(Class<T> clazz, Object... args) {
        try {
            if (args.length == 0) {
                return clazz.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = getClassesForArgs(args);
                return clazz.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?>[] getClassesForArgs(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
}
