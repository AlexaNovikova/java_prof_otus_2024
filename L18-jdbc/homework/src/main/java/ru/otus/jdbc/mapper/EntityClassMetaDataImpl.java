package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import ru.otus.crm.annotations.Id;
import ru.otus.exceptions.MapperException;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> type;

    public EntityClassMetaDataImpl(Class<T> tClass) {
        this.type = tClass;
    }

    @Override
    public String getName() {
        return type.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return type.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new MapperException("Entity class must have constructor with no arguments");
        }
    }

    @Override
    public Field getIdField() {
        List<Field> fields = List.of(type.getDeclaredFields());
        int idFieldCounter = 0;
        Field idField = null;
        for (Field field : fields) {
            if (isIdField(field)) {
                idField = field;
                idFieldCounter++;
            }
        }
        if (idFieldCounter != 1) {
            throw new MapperException("Entity class must have one field with Id annotation");
        }
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(type.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(type.getDeclaredFields())
                .filter(f -> !isIdField(f))
                .toList();
    }

    private boolean isIdField(Field field) {
        return field.getAnnotation(Id.class) != null;
    }
}
