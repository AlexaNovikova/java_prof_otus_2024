package ru.otus.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.crm.annotations.Id;

public class ReflectionUtils {

    public static <T> T newInstance(Class<T> clazz, Object... args) {
        try {
            if (args.length == 0) {
                return clazz.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = getClassesForArgs(args);
                clazz.getDeclaredConstructor(classes).setAccessible(true);
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

    public static <T> List<Object> getListOfFieldValues(T entity) {
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            List<Object> values = new ArrayList<>();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    values.add(field.get(entity));
                }
            }
            System.out.println(values);
            return values;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
