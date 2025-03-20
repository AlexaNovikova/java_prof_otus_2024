package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.annotations.Id;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private EntityClassMetaData<T> entityClassMetaData;
    private final String tableTitle;
    private final String idName;
    private final List<Field> fieldsWithoutId;
    private final Logger logger = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        tableTitle = entityClassMetaData.getName();
        fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        idName = entityClassMetaData.getIdField().getAnnotation(Id.class).columnName();
    }

    @Override
    public String getSelectAllSql() {
        String selectAllSql = "select " + idName + ", " + allFieldsWithoutIdToString() + " from " + tableTitle;
        logger.info("Select all sql request: {}", selectAllSql);
        return selectAllSql;
    }

    private String allFieldsWithoutIdToString() {
        return fieldsWithoutId.stream().map(Field::getName).collect(Collectors.joining(", "));
    }

    @Override
    public String getSelectByIdSql() {
        String selectByIdSql = "select " + idName + ", " + allFieldsWithoutIdToString() + " from " + tableTitle
                + " where " + idName + " = ?";
        logger.info("Select by Id sql request: {}", selectByIdSql);
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        String insertSql = "insert into " + tableTitle + "(" + allFieldsWithoutIdToString() + ") values ("
                + generateStringQuestionsForInsert() + ")";
        logger.info("Insert sql request: {}", insertSql);
        return insertSql;
    }

    private String generateStringQuestionsForInsert() {
        int count = fieldsWithoutId.size();
        StringBuilder builder = new StringBuilder();
        builder.append("?,".repeat(count));
        return builder.substring(0, builder.length() - 1);
    }

    @Override
    public String getUpdateSql() {
        String updateSql = "update " + tableTitle + "  set " + updateValues() + " where " + idName + " = ?";
        logger.info("Update Sql request: {}", updateSql);
        return updateSql;
    }

    private String updateValues() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldsWithoutId.size(); i++) {
            stringBuilder.append(fieldsWithoutId.get(i).getName());
            stringBuilder.append(" = ?,");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
