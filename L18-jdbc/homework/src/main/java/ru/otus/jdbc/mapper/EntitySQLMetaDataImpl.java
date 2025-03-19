package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.crm.annotations.Id;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private EntityClassMetaData<T> entityClassMetaData;
    private final String tableTitle;
    private final String idName;
    private final List<Field> fieldsWithoutId;

    public EntitySQLMetaDataImpl (EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        tableTitle = entityClassMetaData.getName();
        fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        idName = entityClassMetaData.getIdField().getAnnotation(Id.class).columnName();
    }

    @Override
    public String getSelectAllSql() {
        System.out.println("select " + idName + ", " + allFieldsWithoutIdToString() + " from " + tableTitle);
        return "select " + idName + ", " + allFieldsWithoutIdToString() + " from " + tableTitle;
    }

    private String allFieldsWithoutIdToString() {
        return fieldsWithoutId.stream().map(Field::getName).collect(Collectors.joining(", "));
    }

    @Override
    public String getSelectByIdSql() {
        return "select " + idName + ", " + allFieldsWithoutIdToString() + " from " + tableTitle + " where " + idName
                + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "insert into " + tableTitle + "(" + allFieldsWithoutIdToString() + ") values ("
                + generateStringQuestionsForInsert() + ")";
    }

    private String generateStringQuestionsForInsert() {
        int count = fieldsWithoutId.size();
        StringBuilder builder = new StringBuilder();
        builder.append("?,".repeat(count));
        return builder.substring(0, builder.length() - 1);
    }

    @Override
    public String getUpdateSql() {
        return "update " + tableTitle + "  set " + allFieldsWithoutIdToString() + " = " + " where " + idName+" = ?";
    }

    private String updateValues(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldsWithoutId.size(); i++) {
            stringBuilder.append(fieldsWithoutId)
        }
    }
}
