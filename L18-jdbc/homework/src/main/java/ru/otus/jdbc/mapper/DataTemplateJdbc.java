package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.utils.ReflectionUtils;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    List<Field> allFields = entityClassMetaData.getAllFields();
                    T instance = ReflectionUtils.newInstance(
                            entityClassMetaData.getConstructor().getDeclaringClass());
                    int j = 1;
                    for (int i = 0; i < allFields.size(); i++) {
                        allFields.get(i).setAccessible(true);
                        System.out.println(allFields.get(i).getName());
                        System.out.println(rs.getObject(j, allFields.get(i).getType()));
                        allFields
                                .get(i)
                                .set(instance, rs.getObject(j, allFields.get(i).getType()));
                        j++;
                    }
                    return instance;
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var entityList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            entityList.add(newEntityFromResultSet(rs));
                        }
                        return entityList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    private T newEntityFromResultSet(ResultSet rs) {
        try {
            List<Field> allFields = entityClassMetaData.getAllFields();
            List<Object> args = new ArrayList<>();
            int j = 1;
            for (int i = 0; i < allFields.size(); i++) {
                if (rs.getObject(j, allFields.get(i).getType()) != null) {
                    args.add(rs.getObject(j, allFields.get(i).getType()));
                    j++;
                }
            }
            Object[] argsArray = args.toArray();
            return ReflectionUtils.newInstance(
                    entityClassMetaData.getConstructor().getDeclaringClass(), argsArray);
        } catch (SQLException e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public long insert(Connection connection, T entity) {
        try {
            return dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getInsertSql(), ReflectionUtils.getListOfFieldValues(entity, false));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T entity) {
        try {
            dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getUpdateSql(), ReflectionUtils.getListOfFieldValues(entity, true));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
