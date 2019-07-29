package com.chendehe.creator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.chendehe.entity.Field;
import com.chendehe.entity.Table;
import com.chendehe.utils.FileUtils;

/**
 * @author CDH
 * @since 2019/7/29 11:01
 */

public class DaoCreator extends AbstractCreator {

    public DaoCreator(Table table) {
        super(table);
    }

    public void createDao() {
        table.getTableFields()
            .forEach((longTableName, fields) -> FileUtils.writeToFile(
                FileUtils.createFile(table.getDaoFolder().concat(getDaoName(longTableName).concat(JAVA))),
                createDaoFileContent(longTableName)));

        //
        String template = FileUtils.getTemplate("common/BaseDao.java");
        template = template.replaceAll("#<daoPackage>", table.getDaoPackage());
        template = template.replaceAll("#<createUser>", System.getProperty("user.name"));
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        template = template.replaceAll("#<createTime>", createTime);
        FileUtils.writeToFile(FileUtils.createFile(table.getDaoFolder().concat("BaseDao.java")), template);
    }

    private String createDaoFileContent(String longTableName) {
        final String[] tableNames = longTableName.split(SPLIT_CHAR);
        final String tableName = tableNames[0];
        String fileContent = FileUtils.getTemplate(DAO_TEMP);
        final String tableComment = tableNames.length > 1 ? tableNames[1] : "";
        fileContent = fileContent.replaceAll("#<tableComment>", tableComment);
        fileContent = fileContent.replaceAll("#<tableName>", tableName);

        fileContent = fileContent.replaceAll("#<fields>", getFields(longTableName));
        fileContent = fileContent.replaceAll("#<insertValues>", getInsertValues(longTableName));
        fileContent = fileContent.replaceAll("#<batchInsertValues>", getBatchInsertValues(longTableName));
        fileContent = fileContent.replaceAll("#<updateValues>", getUpdateValues(longTableName));
        fileContent = fileContent.replaceAll("#<daoPackage>", table.getDaoPackage());
        fileContent = fileContent.replaceAll("#<daoName>", getDaoName(tableName));
        // model
        fileContent = fileContent.replaceAll("#<modelPackage>", table.getModelPackage());
        fileContent = fileContent.replaceAll("#<modelName>", getModelName(tableName));

        fileContent = fileContent.replaceAll("#<createUser>", System.getProperty("user.name"));
        final String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        fileContent = fileContent.replaceAll("#<createTime>", createTime);
        return fileContent;
    }

    private String getUpdateValues(String tableName) {
        List<Field> fields = table.getTableFields().get(tableName);
        StringBuilder builder = new StringBuilder();
        Field field = fields.get(0);
        String rowContent =
            field.getFieldName() + " = #{#<property>}".replaceAll("#<property>", field.getJavaFieldName());
        builder.append(rowContent);

        for (int i = 1; i < fields.size(); i++) {
            field = fields.get(i);
            rowContent = field.getFieldName() + " = #{#<property>}".replaceAll("#<property>", field.getJavaFieldName());
            builder.append(", ").append(rowContent);
        }
        return builder.toString();
    }

    private String getBatchInsertValues(String tableName) {
        List<Field> fields = table.getTableFields().get(tableName);
        StringBuilder builder = new StringBuilder();
        Field field = fields.get(0);
        String rowContent = "#{#<property>}".replaceAll("#<property>", "n." + field.getJavaFieldName());
        builder.append(rowContent);

        for (int i = 1; i < fields.size(); i++) {
            field = fields.get(i);
            rowContent = "#{#<property>}".replaceAll("#<property>", "n." + field.getJavaFieldName());
            builder.append(", ").append(rowContent);
        }
        return builder.toString();
    }

    private String getInsertValues(String tableName) {
        List<Field> fields = table.getTableFields().get(tableName);
        StringBuilder builder = new StringBuilder();
        Field field = fields.get(0);
        String rowContent = "#{#<property>}".replaceAll("#<property>", field.getJavaFieldName());
        builder.append(rowContent);

        for (int i = 1; i < fields.size(); i++) {
            field = fields.get(i);
            rowContent = "#{#<property>}".replaceAll("#<property>", field.getJavaFieldName());
            builder.append(", ").append(rowContent);
        }
        return builder.toString();
    }

    private String getFields(String tableName) {
        List<Field> fields = table.getTableFields().get(tableName);
        StringBuilder builder = new StringBuilder();
        builder.append(fields.get(0).getFieldName());
        for (int i = 1; i < fields.size(); i++) {
            Field field = fields.get(i);
            builder.append(", ").append(field.getFieldName());
        }
        return builder.toString();
    }
}
